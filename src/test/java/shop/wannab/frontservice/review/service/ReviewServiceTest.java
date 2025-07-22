package shop.wannab.frontservice.review.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import shop.wannab.frontservice.global.minio.BucketType;
import shop.wannab.frontservice.global.minio.MinioService;
import shop.wannab.frontservice.global.response.ApiResponse;
import shop.wannab.frontservice.global.response.PageableInfo;
import shop.wannab.frontservice.global.response.SortInfo;
import shop.wannab.frontservice.review.client.ReviewClient;
import shop.wannab.frontservice.review.client.request.CreateReviewFeignRequest;
import shop.wannab.frontservice.review.client.request.UpdateReviewFeignRequest;
import shop.wannab.frontservice.review.client.response.ReviewListResponse;
import shop.wannab.frontservice.review.client.response.UserReviewListResponse;
import shop.wannab.frontservice.review.controller.request.CreateReviewRequest;
import shop.wannab.frontservice.review.controller.request.UpdateReviewRequest;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewClient reviewClient;

    @Mock
    private MinioService minioService;

    @Mock
    private MultipartFile mockMultipartFile;

    @InjectMocks
    private ReviewService reviewService;

    private ReviewListResponse reviewListResponse;
    private UserReviewListResponse userReviewListResponse;

    @BeforeEach
    void setUp() {
        reviewListResponse = new ReviewListResponse(Collections.emptyList(),
            new PageableInfo(1, 1, new SortInfo(true, true, true), 1, true, true), true, 1, 1, true, 1, 1,
            new SortInfo(true, true, true), 1, true);
        userReviewListResponse = new UserReviewListResponse(Collections.emptyList(),
            new PageableInfo(1, 1, new SortInfo(true, true, true), 1, true, true), true, 1, 1, true, 1, 1,
            new SortInfo(true, true, true), 1, true);
    }

    @Test
    @DisplayName("도서 리뷰 목록 조회")
    void getBookReviews() {
        given(reviewClient.getBookReviews(anyLong())).willReturn(new ApiResponse<>("success", reviewListResponse, null));

        ReviewListResponse response = reviewService.getBookReviews(1L);

        assertEquals(reviewListResponse, response);
        verify(reviewClient).getBookReviews(anyLong());
    }

    @Test
    @DisplayName("도서 리뷰 평균 조회")
    void getBookReviewsAverage() {
        given(reviewClient.getBookReviewsAverage(anyLong())).willReturn(new ApiResponse<>("success", 4.5, null));

        Double average = reviewService.getBookReviewsAverage(1L);

        assertEquals(4.5, average);
        verify(reviewClient).getBookReviewsAverage(anyLong());
    }

    @Test
    @DisplayName("내 리뷰 목록 조회")
    void getMyReviews() {
        given(reviewClient.getMyReview()).willReturn(new ApiResponse<>("success", userReviewListResponse, null));

        UserReviewListResponse response = reviewService.getMyReviews();

        assertEquals(userReviewListResponse, response);
        verify(reviewClient).getMyReview();
    }

    @Test
    @DisplayName("리뷰 생성")
    void createReview() {
        given(minioService.uploadFiles(any(MultipartFile[].class), any(BucketType.class))).willReturn("image-url");
        doNothing().when(reviewClient).createReview(anyLong(), any(CreateReviewFeignRequest.class));

        CreateReviewRequest request = new CreateReviewRequest();
        request.setReviewImages(new MultipartFile[]{mockMultipartFile});
        request.setContent("test content");
        request.setRating(5);
        request.setBookName("test book");
        request.setOrderId(1L);
        request.setBookId(1L);

        reviewService.createReview(request);

        verify(minioService).uploadFiles(any(MultipartFile[].class), any(BucketType.class));
        verify(reviewClient).createReview(anyLong(), any(CreateReviewFeignRequest.class));
    }

    @Test
    @DisplayName("리뷰 수정")
    void updateReview() {
        given(minioService.uploadFiles(any(MultipartFile[].class), any(BucketType.class))).willReturn("new-image-url");
        doNothing().when(reviewClient).updateReview(anyLong(), any(UpdateReviewFeignRequest.class));

        UpdateReviewRequest request = new UpdateReviewRequest();
        request.setReviewImages(new MultipartFile[]{mockMultipartFile});
        request.setContent("updated content");
        request.setRating(4);
        request.setReviewId(1L);

        reviewService.updateReview(request);

        verify(minioService).uploadFiles(any(MultipartFile[].class), any(BucketType.class));
        verify(reviewClient).updateReview(anyLong(), any(UpdateReviewFeignRequest.class));
    }
}