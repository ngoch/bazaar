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
            Customer c1 = new Customer();
            c1.setName("Demo Customer");
            c1.setAmountAvailable(5000);
            customerRepository.save(c1);

            Customer c2 = new Customer();
            c2.setName("Demo Customer 2");
            c2.setAmountAvailable(10);
            customerRepository.save(c2);
        };
    }
}
