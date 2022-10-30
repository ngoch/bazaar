package io.github.ngoch.bazaar.stock.api;

import io.github.ngoch.bazaar.domain.Order;
import io.github.ngoch.bazaar.stock.domain.Product;
import io.github.ngoch.bazaar.stock.service.OrderManageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/stock")
@RestController
public class StockRestController {

    private final OrderManageService orderManageService;

    public StockRestController(OrderManageService orderManageService) {
        this.orderManageService = orderManageService;
    }

    @GetMapping("/test")
    public List<Product> test() {
        return orderManageService.test();
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
