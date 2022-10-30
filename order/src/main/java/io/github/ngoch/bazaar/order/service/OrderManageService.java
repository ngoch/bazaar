package io.github.ngoch.bazaar.order.service;

import io.github.ngoch.bazaar.domain.Order;
import io.github.ngoch.bazaar.order.domain.CustomerOrder;
import io.github.ngoch.bazaar.order.mapper.CustomerOrderMapper;
import io.github.ngoch.bazaar.order.repository.CustomerOrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class OrderManageService {

    private final CustomerOrderMapper orderMapper;
    private final CustomerOrderRepository orderRepository;
    private final PaymentClientService paymentClientService;
    private final StockClientService stockClientService;

    public OrderManageService(CustomerOrderMapper orderMapper, CustomerOrderRepository orderRepository, PaymentClientService paymentClientService, StockClientService stockClientService) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.paymentClientService = paymentClientService;
        this.stockClientService = stockClientService;
    }

    public Order create(Order order) {
        order.setId(UUID.randomUUID().toString());
        order.setStatus("NEW");
        order.setSource("Order");
        CustomerOrder customerOrder = orderMapper.map(order);
        orderRepository.save(customerOrder);
        return order;
    }

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    public void processPayment() {
        for (CustomerOrder co : orderRepository.findAllByStatus("NEW")) {
            Order order = orderMapper.map(co);
            if (co.getPaymentStatus() == null) {
                co.setPaymentStatus(paymentClientService.reserve(order).getStatus());
                updateCustomerOrder(co);
                log.info("processPayment:" + co);
            }
        }
    }

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    public void processStock() {
        for (CustomerOrder co : orderRepository.findAllByStatus("NEW")) {
            Order order = orderMapper.map(co);
            if (co.getStockStatus() == null) {
                co.setStockStatus(stockClientService.reserve(order).getStatus());
                updateCustomerOrder(co);
                log.info("processStock:" + co);
            }
        }
    }

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    public void processCustomerOrder() {
        log.info("Process customer order ...");
        for (CustomerOrder co : orderRepository.findAllByStatus("NEW")) {
            if (co.getStockStatus() != null && co.getPaymentStatus() != null) {
                try {
                    Order paymentOrder = orderMapper.map(co);
                    paymentOrder.setStatus(co.getPaymentStatus());

                    Order stockOrder = orderMapper.map(co);
                    stockOrder.setStatus(co.getStockStatus());

                    Order finalOrder = confirm(paymentOrder, stockOrder);
                    co.setStatus(finalOrder.getStatus());
                    co.setSource(finalOrder.getSource());

                    if (!Objects.equals(finalOrder.getSource(), "STOCK")) {
                        stockClientService.confirm(finalOrder);
                    }

                    if (!Objects.equals(finalOrder.getSource(), "PAYMENT")) {
                        paymentClientService.confirm(finalOrder);
                    }

                    updateCustomerOrder(co);
                    log.info("Processed:" + co);
                } catch (Throwable t) {
                    log.error(t.getMessage(), t);
                    co.setStatus("ERROR");
                }
            }
        }
        log.info("Finish customer order processing.");
    }

    @Transactional
    public void updateCustomerOrder(CustomerOrder co) {
        orderRepository.save(co);
    }


    public Order confirm(Order orderPayment, Order orderStock) {
        Order o = new Order(orderPayment.getId(),
                orderPayment.getCustomerId(),
                orderPayment.getProductId(),
                orderPayment.getProductCount(),
                orderPayment.getPrice());
        if (orderPayment.getStatus().equals("ACCEPT") &&
                orderStock.getStatus().equals("ACCEPT")) {
            o.setStatus("CONFIRMED");
        } else if (orderPayment.getStatus().equals("REJECT") &&
                orderStock.getStatus().equals("REJECT")) {
            o.setStatus("REJECTED");
        } else if (orderPayment.getStatus().equals("REJECT") ||
                orderStock.getStatus().equals("REJECT")) {
            String source = orderPayment.getStatus().equals("REJECT")
                    ? "PAYMENT" : "STOCK";
            o.setStatus("ROLLBACK");
            o.setSource(source);
        }
        return o;
    }

    public List<CustomerOrder> test() {
        List<CustomerOrder> result = new ArrayList<>();
        for (CustomerOrder co : orderRepository.findAll()) {
            result.add(co);
        }
        return result;
    }
}
