package io.github.ngoch.bazaar.payment.service;

import io.github.ngoch.bazaar.domain.Order;
import io.github.ngoch.bazaar.payment.domain.Customer;
import io.github.ngoch.bazaar.payment.domain.CustomerOrder;
import io.github.ngoch.bazaar.payment.repository.CustomerOrderRepository;
import io.github.ngoch.bazaar.payment.repository.CustomerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class OrderManageService {

    private static final String SOURCE = "payment";
    private final CustomerRepository repository;
    private final CustomerOrderRepository customerOrderRepository;

    public OrderManageService(CustomerRepository repository, CustomerOrderRepository customerOrderRepository) {
        this.repository = repository;
        this.customerOrderRepository = customerOrderRepository;
    }

    public Order reserve(Order order) {
        order.setSource(SOURCE);

        Customer customer = repository.findById(order.getCustomerId()).orElseThrow();

        Optional<CustomerOrder> customerOrder = customerOrderRepository.findByOrderId(order.getId());
        if (customerOrder.isPresent()) {
            order.setStatus("REJECT");
            log.info("Order {} already processed", order);
            return order;
        }

        log.info("Found: {}", customer);
        if (order.getPrice() <= customer.getAmountAvailable()) {
            order.setStatus("ACCEPT");
            customer.setAmountReserved(customer.getAmountReserved() + order.getPrice());
            customer.setAmountAvailable(customer.getAmountAvailable() == 0 ? 0 : customer.getAmountAvailable() - order.getPrice());
            repository.save(customer);
        } else {
            order.setStatus("REJECT");
        }

        CustomerOrder co = CustomerOrder.builder()
                .orderId(order.getId())
                .customer(customer)
                .status(order.getStatus())
                .build();

        customerOrderRepository.save(co);
        log.info("Done: {}", order);

        return order;
    }

    public Order confirm(Order order) {
        Optional<CustomerOrder> productOrder = customerOrderRepository.findByOrderId(order.getId());
        if (productOrder.isEmpty()) {
            order.setStatus("ROLLBACK");
            log.info("Order {} doesn't exist", order);
            return order;
        }

        Customer customer = repository.findById(order.getCustomerId()).orElseThrow();
        log.info("Found: {}", customer);
        if (order.getStatus().equals("CONFIRMED")) {
            customer.setAmountReserved(customer.getAmountReserved() - order.getPrice());
            repository.save(customer);
        } else if (order.getStatus().equals("ROLLBACK") && !order.getSource().equals(SOURCE)) {
            customer.setAmountReserved(customer.getAmountReserved() - order.getPrice());
            customer.setAmountAvailable(customer.getAmountAvailable() + order.getPrice());
            repository.save(customer);
        }
        return order;
    }

    public List<Customer> test() {
        List<Customer> customers = new ArrayList<>();
        for (Customer c : repository.findAll()) {
            customers.add(c);
        }
        return customers;
    }
}
