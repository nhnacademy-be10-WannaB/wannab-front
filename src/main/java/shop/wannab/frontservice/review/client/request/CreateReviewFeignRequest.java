package shop.wannab.frontservice.review.client.request;


import java.time.LocalDateTime;

public record CreateReviewFeignRequest (
        String reviewContent,
        Integer reviewScore,
        String bookName,
        LocalDateTime reviewCreatedAt,
        Long obId,
        String reviewImages
){
}


