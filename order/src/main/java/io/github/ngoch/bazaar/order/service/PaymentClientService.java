package io.github.ngoch.bazaar.order.service;

import io.github.ngoch.bazaar.domain.Order;
import io.github.ngoch.bazaar.order.config.OrderProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentClientService {

    private final RestTemplate restTemplate;
    private final OrderProperties orderProperties;

    public PaymentClientService(RestTemplate restTemplate, OrderProperties orderProperties) {
        this.restTemplate = restTemplate;
        this.orderProperties = orderProperties;
    }

    public Order reserve(Order order) {
        return restTemplate.postForObject(orderProperties.getPaymentUrl() + "/payment/reserve", order, Order.class);
    }

    public void confirm(Order order) {
        restTemplate.postForObject(orderProperties.getPaymentUrl() + "/payment/confirm", order, Order.class);
    }
}
