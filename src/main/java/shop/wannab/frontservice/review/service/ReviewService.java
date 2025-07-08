package shop.wannab.frontservice.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.global.response.ApiResponse;
import shop.wannab.frontservice.review.client.ReviewClient;
import shop.wannab.frontservice.review.client.response.ReviewResponse;
import shop.wannab.frontservice.review.client.response.ReviewListResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewClient reviewClient;

    public ReviewListResponse getBookReviews(Long bookId){
        ApiResponse<ReviewListResponse> response = reviewClient.getBookReviews(bookId);
        return response.data();
    }

    public Double getBookReviewsAverage(Long bookId){
        ApiResponse<Double> response = reviewClient.getBookReviewsAverage(bookId);
        return response.data();
    }
}
