package io.github.ngoch.bazaar.stock.service;

import io.github.ngoch.bazaar.domain.Order;
import io.github.ngoch.bazaar.stock.domain.Product;
import io.github.ngoch.bazaar.stock.domain.ProductOrder;
import io.github.ngoch.bazaar.stock.repository.ProductOrderRepository;
import io.github.ngoch.bazaar.stock.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class OrderManageService {

    private static final String SOURCE = "stock";

    private final ProductRepository repository;
    private final ProductOrderRepository productOrderRepository;

    public OrderManageService(ProductRepository repository, ProductOrderRepository productOrderRepository) {
        this.repository = repository;
        this.productOrderRepository = productOrderRepository;
    }

    public Order reserve(Order order) {
        order.setSource(SOURCE);
        Product product = repository.findById(order.getProductId()).orElseThrow();
        Optional<ProductOrder> productOrder = productOrderRepository.findByOrderId(order.getId());
        if (productOrder.isPresent()) {
            order.setStatus("REJECT");
            log.info("Order {} already processed", order);
            return order;
        }
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
            ProductOrder po = ProductOrder.builder()
                    .product(product)
                    .orderId(order.getId())
                    .status(order.getStatus())
                    .build();

            productOrderRepository.save(po);
            log.info("Done: {}", order);
        }

        return order;
    }

    public Order confirm(Order order) {
        Product product = repository.findById(order.getProductId()).orElseThrow();
        Optional<ProductOrder> productOrder = productOrderRepository.findByOrderId(order.getId());
        if (productOrder.isEmpty()) {
            order.setStatus("ROLLBACK");
            log.info("Order {} doesn't exist", order);
            return order;
        }
        log.info("Found: {}", product);
        if (order.getStatus().equals("CONFIRMED")) {
            product.setReservedItems(product.getReservedItems() - order.getProductCount());
            repository.save(product);
        } else if (order.getStatus().equals("ROLLBACK") && !order.getSource().equals(SOURCE)) {
            product.setReservedItems(product.getReservedItems() - order.getProductCount());
            product.setAvailableItems(product.getAvailableItems() + order.getProductCount());
            repository.save(product);
        }
        return order;
    }

    public List<Product> test() {
        List<Product> products = new ArrayList<>();
        for (Product p : repository.findAll()) {
            products.add(p);
        }
        return products;
    }
}
