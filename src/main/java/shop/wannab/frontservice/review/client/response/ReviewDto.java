package shop.wannab.frontservice.review.client.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import shop.wannab.frontservice.global.jackson.LocalDateTimeDeserializer;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ReviewDto {
    private String reviewContent;
    private int reviewScore;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime reviewCreatedAt;

    private String username;
    private List<ReviewImageDto> reviewImages;
}
