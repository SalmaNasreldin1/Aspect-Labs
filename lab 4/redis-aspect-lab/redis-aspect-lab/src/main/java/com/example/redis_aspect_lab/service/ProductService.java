package com.example.redis_aspect_lab.service;

import com.example.redis_aspect_lab.annotation.DistributedLock;
import com.example.redis_aspect_lab.annotation.RateLimit;
import com.example.redis_aspect_lab.annotation.RedisCache;
import com.example.redis_aspect_lab.entity.Product;
import com.example.redis_aspect_lab.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @RedisCache(key = "allProducts", expiration = 60)
    public List<Product> getAllProducts() {
        // Simulate database query delay
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return productRepository.findAll();
    }

    @RedisCache(key = "'product:' + #id", expiration = 60)
    public Optional<Product> getProductById(Long id) {
        // Simulate database query delay
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return productRepository.findById(id);
    }

    @RateLimit(limit = 2, duration = 60)
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @DistributedLock(key = "'updateProduct:' + #product.id", expiration = 30)
    public Product updateProduct(Product product) {
        // Simulate long-running operation
        try {
            Thread.sleep(10000); // 10 seconds delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return productRepository.save(product);
    }

    @DistributedLock(key = "'deleteProduct:' + #id", expiration = 30)
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
