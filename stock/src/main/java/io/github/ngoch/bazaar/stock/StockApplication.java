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
            Product p1 = new Product();
            p1.setName("Demo Product");
            p1.setAvailableItems(2000);
            p1.setReservedItems(0);
            productRepository.save(p1);

            Product p2 = new Product();
            p2.setName("Demo Product");
            p2.setAvailableItems(10);
            p2.setReservedItems(0);
            productRepository.save(p2);
        };
    }
}
