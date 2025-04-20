package com.example.redis_aspect_lab;

import com.example.redis_aspect_lab.entity.Product;
import com.example.redis_aspect_lab.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RedisAspectLabApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisAspectLabApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(ProductRepository repository) {
		return args -> {
			repository.save(new Product(null, "Laptop", "High-performance laptop", 1299.99));
			repository.save(new Product(null, "Smartphone", "Latest model", 899.99));
			repository.save(new Product(null, "Headphones", "Noise-cancelling", 249.99));
		};
	}
}