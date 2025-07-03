package shop.wannab.frontservice.review.client.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import shop.wannab.frontservice.global.jackson.LocalDateTimeDeserializer;

import java.time.LocalDateTime;
import java.util.List;

public record ReviewResponse(
    String reviewContent,
    int reviewScore,
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime reviewCreatedAt,
    String username,
    List<ReviewImageResponse> reviewImages
) {
}
