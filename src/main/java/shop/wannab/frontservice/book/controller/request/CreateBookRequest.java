package shop.wannab.frontservice.book.controller.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;

@Getter
@Setter
public class CreateBookRequest {

    private String title;
    private String description;
    private LocalDate publicationDate;
    private String isbn;

    private Integer originPrice;
    private Integer salesPrice;

    private Integer stock;
    private Boolean status;

    private String bookChapter;

    private String category;
    private String author;
    private String publisher;
    private String bookTags;

    private MultipartFile[] bookImages;
}
