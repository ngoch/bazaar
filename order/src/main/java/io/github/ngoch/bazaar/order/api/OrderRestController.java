package io.github.ngoch.bazaar.order.api;

import io.github.ngoch.bazaar.domain.Order;
import io.github.ngoch.bazaar.order.service.OrderManageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/order")
public class OrderRestController {

    private final OrderManageService orderManageService;

    public OrderRestController(OrderManageService orderManageService) {
        this.orderManageService = orderManageService;
    }

    @PostMapping("/create")
    public Order create(@RequestBody Order order) {
        order = orderManageService.create(order);
        log.info("Sent: {}", order);
        return order;
    }

    @GetMapping("/all")
    public List<Order> all() {
        return orderManageService.getAll();
    }
}
