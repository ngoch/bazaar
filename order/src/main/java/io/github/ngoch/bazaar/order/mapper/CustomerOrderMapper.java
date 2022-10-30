package io.github.ngoch.bazaar.order.mapper;

import io.github.ngoch.bazaar.domain.Order;
import io.github.ngoch.bazaar.order.domain.CustomerOrder;
import org.springframework.stereotype.Service;

@Service
public class CustomerOrderMapper {

    public CustomerOrder map(Order order) {
        if (order == null) {
            return null;
        }

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setId(order.getId());
        customerOrder.setProductId(order.getProductId());
        customerOrder.setCustomerId(order.getCustomerId());
        customerOrder.setProductId(order.getProductId());
        customerOrder.setProductCount(order.getProductCount());
        customerOrder.setPrice(order.getPrice());
        customerOrder.setStatus(order.getStatus());
        customerOrder.setSource(order.getSource());
        return customerOrder;
    }

    public Order map(CustomerOrder co) {
        if (co == null) {
            return null;
        }
        Order order = new Order();
        order.setId(co.getId());
        order.setProductId(co.getProductId());
        order.setCustomerId(co.getCustomerId());
        order.setProductId(co.getProductId());
        order.setProductCount(co.getProductCount());
        order.setPrice(co.getPrice());
        order.setStatus(co.getStatus());
        order.setSource(co.getSource());
        return order;
    }
}

