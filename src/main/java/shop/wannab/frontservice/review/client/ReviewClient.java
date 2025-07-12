package shop.wannab.frontservice.review.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import shop.wannab.frontservice.global.response.ApiResponse;
import shop.wannab.frontservice.review.client.request.CreateReviewFeignRequest;
import shop.wannab.frontservice.review.client.request.UpdateReviewFeignRequest;
import shop.wannab.frontservice.review.client.response.ReviewListResponse;
import shop.wannab.frontservice.review.client.response.UserReviewListResponse;
import shop.wannab.frontservice.review.client.response.UserReviewResponse;

@FeignClient(name = "gateway", url = "${gateway.api.url}", path = "/book-service", contextId = "reviewClient")
public interface ReviewClient {
    @GetMapping("/api/reviews/books/{bookId}")
    ApiResponse<ReviewListResponse> getBookReviews(@PathVariable("bookId") Long bookId);

    @GetMapping("/api/reviews/books/{bookId}/average")
    ApiResponse<Double> getBookReviewsAverage(@PathVariable("bookId") Long bookId);

    @PostMapping("/api/reviews/books/{bookId}")
    void createReview(@PathVariable("bookId") Long bookId, CreateReviewFeignRequest createReviewFeignRequest);

    @GetMapping("/api/reviews/me")
    ApiResponse<UserReviewListResponse> getMyReview();

    @PutMapping("/api/reviews/{reviewId}")
    void updateReview(@PathVariable("reviewId") Long reviewId, UpdateReviewFeignRequest request);
}
