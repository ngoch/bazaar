package io.github.ngoch.bazaar.order.service;

import io.github.ngoch.bazaar.domain.Order;
import io.github.ngoch.bazaar.order.config.OrderProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StockClientService {

    private final RestTemplate restTemplate;
    private final OrderProperties orderProperties;

    public StockClientService(RestTemplate restTemplate, OrderProperties orderProperties) {
        this.restTemplate = restTemplate;
        this.orderProperties = orderProperties;
    }

    public Order reserve(Order order) {
        order = restTemplate.postForObject(orderProperties.getStockUrl() + "/stock/reserve", order, Order.class);
        return order;
    }

    public void confirm(Order order) {
        restTemplate.postForObject(orderProperties.getStockUrl() + "/stock/confirm", order, Order.class);
    }
}
