package io.github.ngoch.bazaar.order.controller;

import io.github.ngoch.bazaar.domain.Order;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/orders")
public class OrderController {

    @PostMapping
    public Order create(@RequestBody Order order) {
        //TODO ....
//        template.send("orders", order.getId(), order);
        log.info("Sent: {}", order);
        return order;
    }

    @GetMapping
    public List<Order> all() {
        List<Order> orders = new ArrayList<>();
        //TODO ...
        return orders;
    }
}
