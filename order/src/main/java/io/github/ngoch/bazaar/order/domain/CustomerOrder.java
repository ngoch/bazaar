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
public class CustomerOrder{

    @Id
    private String id;

    private Long customerId;
    private Long productId;
    private int productCount;
    private int price;
    private String status;

    private String stockStatus;
    private String paymentStatus;
    private String source;
}
