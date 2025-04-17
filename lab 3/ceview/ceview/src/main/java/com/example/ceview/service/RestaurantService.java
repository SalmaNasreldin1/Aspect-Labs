package com.example.ceview.service;

import com.example.ceview.model.Restaurant;
import com.example.ceview.repository.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(UUID id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found with id: " + id));
    }

    public List<Restaurant> getRestaurantsByCategory(Restaurant.RestaurantCategory category) {
        return restaurantRepository.findByCategory(category);
    }

    public List<Restaurant> searchRestaurantsByName(String name) {
        return restaurantRepository.findByNameContainingIgnoreCase(name);
    }

    @Transactional
    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public Restaurant updateRestaurant(UUID id, Restaurant restaurantDetails) {
        Restaurant restaurant = getRestaurantById(id);

        restaurant.setName(restaurantDetails.getName());
        restaurant.setAddress(restaurantDetails.getAddress());
        restaurant.setCategory(restaurantDetails.getCategory());
        restaurant.setDescription(restaurantDetails.getDescription());
        restaurant.setContactNumber(restaurantDetails.getContactNumber());
        restaurant.setOpeningHours(restaurantDetails.getOpeningHours());
        restaurant.setImageUrl(restaurantDetails.getImageUrl());

        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public void deleteRestaurant(UUID id) {
        Restaurant restaurant = getRestaurantById(id);
        restaurantRepository.delete(restaurant);
    }
}