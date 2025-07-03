package shop.wannab.frontservice.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.global.response.ApiResponse;
import shop.wannab.frontservice.review.client.ReviewClient;
import shop.wannab.frontservice.review.client.response.ReviewDto;
import shop.wannab.frontservice.review.client.response.ReviewListDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewClient reviewClient;

    public List<ReviewDto> getBookReviews(Long bookId){
        ApiResponse<ReviewListDto> response = reviewClient.getBookReviews(bookId);
        return response.getData().getContent();
    }
}
