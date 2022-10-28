package io.github.ngoch.bazaar.stock.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/stock")
@RestController
public class StockRestController {

    @GetMapping("/test")
    public String test() {
        return "Test payment";
    }
}
