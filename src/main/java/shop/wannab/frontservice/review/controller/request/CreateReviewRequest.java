package shop.wannab.frontservice.review.controller.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CreateReviewRequest {
        private Long bookId;
        private Long orderId;
        private int rating;
        private String content;
        private String bookName;
        private MultipartFile[] reviewImages;

}