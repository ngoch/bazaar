package io.github.ngoch.bazaar.payment.repository;

import io.github.ngoch.bazaar.payment.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
