package io.github.ngoch.bazaar.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "order")
public class OrderProperties {

    private String paymentUrl;
    private String stockUrl;
}