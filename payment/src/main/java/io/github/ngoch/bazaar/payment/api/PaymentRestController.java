package io.github.ngoch.bazaar.payment.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/payment")
@RestController
public class PaymentRestController {

    @GetMapping("/test")
    public String test()
    {
        return "Test payment";
    }
}
