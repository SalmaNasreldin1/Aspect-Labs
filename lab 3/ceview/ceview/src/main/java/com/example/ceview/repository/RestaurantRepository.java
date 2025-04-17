package com.example.ceview.repository;

import com.example.ceview.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
    List<Restaurant> findByCategory(Restaurant.RestaurantCategory category);
    List<Restaurant> findByNameContainingIgnoreCase(String name);
}