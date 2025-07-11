package shop.wannab.frontservice.review.client.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import shop.wannab.frontservice.global.jackson.LocalDateTimeDeserializer;

import java.time.LocalDateTime;
import java.util.List;

public record UserReviewResponse(
        Long reviewId,
        String reviewContent,
        int reviewScore,
        String bookName,
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        LocalDateTime reviewCreatedAt,
        List<ReviewImageResponse> reviewImages
) {
}
