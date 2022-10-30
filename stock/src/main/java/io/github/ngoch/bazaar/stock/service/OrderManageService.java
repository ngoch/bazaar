package io.github.ngoch.bazaar.stock.service;

import io.github.ngoch.bazaar.domain.Order;
import io.github.ngoch.bazaar.stock.domain.Product;
import io.github.ngoch.bazaar.stock.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class OrderManageService {

    private static final String SOURCE = "stock";

    private final ProductRepository repository;

    public OrderManageService(ProductRepository repository) {
        this.repository = repository;
    }

    public Order reserve(Order order) {
        order.setSource(SOURCE);
        Product product = repository.findById(order.getProductId()).orElseThrow();
        log.info("Found: {}", product);
        if (order.getStatus().equals("NEW")) {
            if (order.getProductCount() <= product.getAvailableItems()) {
                product.setReservedItems(product.getReservedItems() + order.getProductCount());
                product.setAvailableItems(product.getAvailableItems() - order.getProductCount());
                repository.save(product);
                order.setStatus("ACCEPT");
            } else {
                order.setStatus("REJECT");
            }

            //TODO "save order"
            log.info("Sent: {}", order);
        }

        return order;
    }

    public Order confirm(Order order) {
        Product product = repository.findById(order.getProductId()).orElseThrow();
        log.info("Found: {}", product);
        if (order.getStatus().equals("CONFIRMED")) {
            product.setReservedItems(product.getReservedItems() - order.getProductCount());
            repository.save(product);
        } else if (order.getStatus().equals("ROLLBACK") && !order.getSource().equals(SOURCE)) {
            product.setReservedItems(product.getReservedItems() == 0 ? 0 : (product.getReservedItems() - order.getProductCount()));
            product.setAvailableItems(product.getReservedItems() == 0 ? 0 : (product.getAvailableItems() + order.getProductCount()));
            repository.save(product);
        }
        return order;
    }
}
