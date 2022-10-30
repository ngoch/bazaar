package io.github.ngoch.bazaar.stock.repository;

import io.github.ngoch.bazaar.stock.domain.Product;
import io.github.ngoch.bazaar.stock.domain.ProductOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductOrderRepository extends CrudRepository<ProductOrder, Long> {
    Optional<ProductOrder> findByOrderId(String id);
}
