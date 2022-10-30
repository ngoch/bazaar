package io.github.ngoch.bazaar.payment;

import io.github.ngoch.bazaar.payment.domain.Customer;
import io.github.ngoch.bazaar.payment.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(CustomerRepository customerRepository) {
        return args -> {
            Customer c = new Customer();
            c.setName("Demo Customer");
            c.setAmountAvailable(500);
            customerRepository.save(c);
        };
    }
}
