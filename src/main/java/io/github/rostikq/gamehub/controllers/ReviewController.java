package io.github.rostikq.gamehub.controllers;

import io.github.rostikq.gamehub.dto.ReviewRequestDto;
import io.github.rostikq.gamehub.dto.ReviewSubmitDto;
import io.github.rostikq.gamehub.exceptions.IncorrectArgs;
import io.github.rostikq.gamehub.services.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submit(@RequestBody ReviewSubmitDto reviewRequest,
                                         @RequestHeader(HttpHeaders.AUTHORIZATION) String authorHeader) {
        logger.info("Received request to submit review");
        try {
            reviewService.submit(reviewRequest, authorHeader);
        }
        catch (IncorrectArgs e) {
            return ResponseEntity.badRequest().body(
                    e.getMessage());
        }

        return ResponseEntity.ok("Review has been submitted successfully");
    }

    @GetMapping("/game/{id}/recent")
    public ResponseEntity<?> getRecentReviewsOfGame(@PathVariable Long id){
        logger.info("Received request to get recent reviews of game: {}", id);
        List<ReviewRequestDto> reviewList;
        try{
            reviewList = reviewService.getRecentGameReviews(id, 10);
        }
        catch (IncorrectArgs e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        logger.info("Got recent reviews of game: {}", id);
        return ResponseEntity.ok(reviewList);
    }

    @GetMapping("/user/{id}/all")
    public ResponseEntity<?> getUserReviews(@PathVariable Long id){
        logger.info("Received request to get recent reviews of user: {}", id);
        List<ReviewRequestDto> reviewList;
        try {
            reviewList = reviewService.getUserReviews(id);
        }
        catch (IncorrectArgs e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        logger.info("Got recent reviews of user: {}", id);
        return ResponseEntity.ok(reviewList);
    }

}
