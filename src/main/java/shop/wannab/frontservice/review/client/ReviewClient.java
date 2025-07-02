package shop.wannab.frontservice.review.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import shop.wannab.frontservice.global.response.ApiResponse;
import shop.wannab.frontservice.review.client.response.ReviewListResponse;

@FeignClient(name = "gateway", url = "${gateway.api.url}", path = "/book-service", contextId = "reviewClient")
public interface ReviewClient {
    @GetMapping("/api/reviews/books/{bookId}")
    ApiResponse<ReviewListResponse> getBookReviews(@PathVariable("bookId") Long bookId);
}
