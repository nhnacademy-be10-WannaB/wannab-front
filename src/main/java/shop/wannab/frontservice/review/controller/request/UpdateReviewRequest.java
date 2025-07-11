package shop.wannab.frontservice.review.controller.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UpdateReviewRequest {

    private Long reviewId;

    private String bookName;

    private Integer rating;

    private String content;

    private MultipartFile[] reviewImages;

}
