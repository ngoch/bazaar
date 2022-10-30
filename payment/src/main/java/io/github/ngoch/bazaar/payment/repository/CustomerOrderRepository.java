package io.github.ngoch.bazaar.payment.repository;

import io.github.ngoch.bazaar.payment.domain.CustomerOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerOrderRepository extends CrudRepository<CustomerOrder, Long> {
    Optional<CustomerOrder> findByOrderId(String id);
}
