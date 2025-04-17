package com.example.ceview.service;

import com.example.ceview.model.Restaurant;
import com.example.ceview.model.Review;
import com.example.ceview.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantService restaurantService;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(UUID id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + id));
    }

    public List<Review> getReviewsByRestaurantId(UUID restaurantId) {
        // Verify restaurant exists
        restaurantService.getRestaurantById(restaurantId);
        return reviewRepository.findByRestaurantId(restaurantId);
    }

    public List<Review> getReviewsByUserEmail(String userEmail) {
        return reviewRepository.findByUserEmail(userEmail);
    }

    public List<Review> getReportedReviews() {
        return reviewRepository.findByIsReportedTrue();
    }

    @Transactional
    public Review createReview(UUID restaurantId, Review review) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        review.setRestaurant(restaurant);
        return reviewRepository.save(review);
    }

    @Transactional
    public Review updateReview(UUID id, Review reviewDetails) {
        Review review = getReviewById(id);

        review.setRating(reviewDetails.getRating());
        review.setContent(reviewDetails.getContent());

        return reviewRepository.save(review);
    }

    @Transactional
    public Review reportReview(UUID id) {
        Review review = getReviewById(id);
        review.setReported(true);
        return reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(UUID id) {
        Review review = getReviewById(id);
        reviewRepository.delete(review);
    }
}