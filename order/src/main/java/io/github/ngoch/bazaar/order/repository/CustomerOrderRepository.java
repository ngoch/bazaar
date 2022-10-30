package io.github.ngoch.bazaar.order.repository;

import io.github.ngoch.bazaar.order.domain.CustomerOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerOrderRepository extends CrudRepository<CustomerOrder, Long> {

    List<CustomerOrder> findAllByStatus(String status);
}
