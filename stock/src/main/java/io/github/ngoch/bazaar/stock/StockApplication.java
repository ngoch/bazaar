package io.github.ngoch.bazaar.stock;

import io.github.ngoch.bazaar.stock.domain.Product;
import io.github.ngoch.bazaar.stock.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StockApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository) {
        return args -> {
            Product p = new Product();
            p.setName("Demo Product");
            p.setAvailableItems(2);
            p.setReservedItems(0);
            productRepository.save(p);
        };
    }
}
