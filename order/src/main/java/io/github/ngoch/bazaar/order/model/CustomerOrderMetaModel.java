package io.github.ngoch.bazaar.order.model;

public class CustomerOrderMetaModel {

    private String id;

    private Long customerId;
    private Long productId;
    private int productCount;
    private int price;
    private String status;

    private String stockStatus;
    private String paymentStatus;
}
