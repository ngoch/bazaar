package io.github.ngoch.bazaar.stock.api;

import io.github.ngoch.bazaar.domain.Order;
import io.github.ngoch.bazaar.stock.service.OrderManageService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/stock")
@RestController
public class StockRestController {

    private final OrderManageService orderManageService;

    public StockRestController(OrderManageService orderManageService) {
        this.orderManageService = orderManageService;
    }

    @GetMapping("/test")
    public String test() {
        return "Test payment";
    }

    @PostMapping("/reserve")
    public Order reserve(@RequestBody Order order) {
        return orderManageService.reserve(order);
    }

    @PostMapping("/confirm")
    public Order confirm(@RequestBody Order order) {
        return orderManageService.confirm(order);
    }
}
