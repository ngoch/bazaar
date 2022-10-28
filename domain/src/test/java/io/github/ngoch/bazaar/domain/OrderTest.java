package io.github.ngoch.bazaar.domain;

import org.junit.Test;

public class OrderTest {

    @Test
    public void test() {
        Order order = new Order.OrderBuilder()
                .source("Test")
                .build();
        System.out.println(order);
    }
}
