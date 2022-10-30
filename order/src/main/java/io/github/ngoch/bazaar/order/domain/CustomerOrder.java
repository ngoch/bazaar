package io.github.ngoch.bazaar.order.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Builder
public class CustomerOrder{

    @Id
    private String id;

    private Long customerId;
    private Long productId;
    private int productCount;
    private int price;
    private String status;
    private String source;

    private String stockStatus;
    private String paymentStatus;

    public CustomerOrder(String id, Long customerId, Long productId, int productCount, int price, String status, String source, String stockStatus, String paymentStatus) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.productCount = productCount;
        this.price = price;
        this.status = status;
        this.source = source;
        this.stockStatus = stockStatus;
        this.paymentStatus = paymentStatus;
    }
}
