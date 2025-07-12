package shop.wannab.frontservice.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.wannab.frontservice.global.minio.BucketType;
import shop.wannab.frontservice.global.minio.MinioService;
import shop.wannab.frontservice.global.response.ApiResponse;
import shop.wannab.frontservice.review.client.ReviewClient;
import shop.wannab.frontservice.review.client.request.CreateReviewFeignRequest;
import shop.wannab.frontservice.review.client.request.UpdateReviewFeignRequest;
import shop.wannab.frontservice.review.client.response.ReviewResponse;
import shop.wannab.frontservice.review.client.response.ReviewListResponse;
import shop.wannab.frontservice.review.client.response.UserReviewListResponse;
import shop.wannab.frontservice.review.client.response.UserReviewResponse;
import shop.wannab.frontservice.review.controller.request.CreateReviewRequest;
import shop.wannab.frontservice.review.controller.request.UpdateReviewRequest;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewClient reviewClient;
    private final MinioService minioService;

    public ReviewListResponse getBookReviews(Long bookId){
        ApiResponse<ReviewListResponse> response = reviewClient.getBookReviews(bookId);
        return response.data();
    }

    public Double getBookReviewsAverage(Long bookId){
        ApiResponse<Double> response = reviewClient.getBookReviewsAverage(bookId);
        return response.data();
    }
    public UserReviewListResponse getMyReviews(){
        ApiResponse<UserReviewListResponse> response = reviewClient.getMyReview();
        return response.data();
    }
    public void  createReview(CreateReviewRequest request){
        MultipartFile[] reviewImages = request.getReviewImages();
        String imageUrls = minioService.uploadFiles(reviewImages, BucketType.REVIEW);

        CreateReviewFeignRequest feignRequest = new CreateReviewFeignRequest(
                request.getContent(),
                request.getRating(),
                request.getBookName(),
                LocalDateTime.now(),
                request.getOrderId(),
                imageUrls
        );
        reviewClient.createReview(request.getBookId(),feignRequest);
    }

    public void updateReview(UpdateReviewRequest request){
        MultipartFile[] reviewImages = request.getReviewImages();
        String imageUrls = minioService.uploadFiles(reviewImages, BucketType.REVIEW);

        UpdateReviewFeignRequest feignRequest = new UpdateReviewFeignRequest(
                request.getContent(),
                request.getRating(),
                LocalDateTime.now(),
                imageUrls
        );

        reviewClient.updateReview(request.getReviewId(),feignRequest);
    }
}
