package shop.wannab.frontservice.review.client.request;


import java.time.LocalDateTime;

public record UpdateReviewFeignRequest (
        String reviewContent,
        Integer reviewScore,
        LocalDateTime reviewUpdatedAt,
        String reviewImages
){
}
