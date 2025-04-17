package com.example.ceview.controller;

import com.example.ceview.model.Review;
import com.example.ceview.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable UUID id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @GetMapping("/restaurants/{restaurantId}/reviews")
    public ResponseEntity<List<Review>> getReviewsByRestaurantId(@PathVariable UUID restaurantId) {
        return ResponseEntity.ok(reviewService.getReviewsByRestaurantId(restaurantId));
    }

    @GetMapping("/reviews/user")
    public ResponseEntity<List<Review>> getReviewsByUserEmail(@RequestParam String email) {
        return ResponseEntity.ok(reviewService.getReviewsByUserEmail(email));
    }

    @GetMapping("/reviews/reported")
    public ResponseEntity<List<Review>> getReportedReviews() {
        return ResponseEntity.ok(reviewService.getReportedReviews());
    }

    @PostMapping("/restaurants/{restaurantId}/reviews")
    public ResponseEntity<Review> createReview(
            @PathVariable UUID restaurantId,
            @Valid @RequestBody Review review) {
        return new ResponseEntity<>(reviewService.createReview(restaurantId, review), HttpStatus.CREATED);
    }

    @PutMapping("/reviews/{id}")
    public ResponseEntity<Review> updateReview(
            @PathVariable UUID id,
            @Valid @RequestBody Review review) {
        return ResponseEntity.ok(reviewService.updateReview(id, review));
    }

    @PatchMapping("/reviews/{id}/report")
    public ResponseEntity<Review> reportReview(@PathVariable UUID id) {
        return ResponseEntity.ok(reviewService.reportReview(id));
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}