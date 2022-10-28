package io.github.ngoch.bazaar.stock.repository;

import io.github.ngoch.bazaar.stock.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
