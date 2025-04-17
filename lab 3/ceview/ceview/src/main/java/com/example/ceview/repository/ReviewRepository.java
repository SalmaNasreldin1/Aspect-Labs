package com.example.ceview.repository;

import com.example.ceview.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findByRestaurantId(UUID restaurantId);
    List<Review> findByUserEmail(String userEmail);
    List<Review> findByIsReportedTrue();
}