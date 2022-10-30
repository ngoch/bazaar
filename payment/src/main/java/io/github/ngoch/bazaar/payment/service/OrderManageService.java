package io.github.ngoch.bazaar.payment.service;

import io.github.ngoch.bazaar.domain.Order;
import io.github.ngoch.bazaar.payment.domain.Customer;
import io.github.ngoch.bazaar.payment.repository.CustomerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class OrderManageService {

    private static final String SOURCE = "payment";
    private final CustomerRepository repository;

    public OrderManageService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Order reserve(Order order) {
        order.setSource(SOURCE);

        Customer customer = repository.findById(order.getCustomerId()).orElseThrow();
        log.info("Found: {}", customer);
        if (order.getPrice() <= customer.getAmountAvailable()) {
            order.setStatus("ACCEPT");
            customer.setAmountReserved(customer.getAmountReserved() + order.getPrice());
            customer.setAmountAvailable(customer.getAmountAvailable() == 0 ? 0 : customer.getAmountAvailable() - order.getPrice());
            repository.save(customer);
        } else {
            order.setStatus("REJECT");
        }

        //TODO save order
        log.info("Sent: {}", order);

        return order;
    }

    public Order confirm(Order order) {
        Customer customer = repository.findById(order.getCustomerId()).orElseThrow();
        log.info("Found: {}", customer);
        if (order.getStatus().equals("CONFIRMED")) {
            customer.setAmountReserved(customer.getAmountReserved() - order.getPrice());
            repository.save(customer);
        } else if (order.getStatus().equals("ROLLBACK") && !order.getSource().equals(SOURCE)) {
            customer.setAmountReserved(customer.getAmountReserved() == 0 ? 0 : (customer.getAmountReserved() - order.getPrice()));
            customer.setAmountAvailable(customer.getAmountReserved() == 0 ? 0 : (customer.getAmountAvailable() + order.getPrice()));
            repository.save(customer);
        }
        return order;
    }
}
