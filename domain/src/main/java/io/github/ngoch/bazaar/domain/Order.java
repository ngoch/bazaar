package io.github.ngoch.bazaar.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Order{
    private String id;
    private Long customerId;
    private Long productId;
    private int productCount;
    private int price;
    private String status;
    private String source;

    public Order() {
    }

    public Order(String id, Long customerId, Long productId, int productCount, int price) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.productCount = productCount;
        this.price = price;
    }

    public Order(String id, Long customerId, Long productId, int productCount, int price, String status, String source) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.productCount = productCount;
        this.price = price;
        this.status = status;
        this.source = source;
    }
}
