package shop.wannab.frontservice.book.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;

@Getter
@Setter
public class CreateBookRequest {

    @NotBlank private String title;
    private String bookChapter;
    @NotBlank private String description;
    @NotBlank private String category;
    @NotBlank private String author;
    @NotBlank private String publisher;
    @NotNull
    private LocalDate publicationDate;
    @NotBlank private String isbn;

    @NotNull
    @Min(value = 0)
    private Integer originPrice;
    private Integer salesPrice;

    @NotNull
    @Min(value = 0)
    private Integer stock;

    @NotNull private Boolean status;

    @NotBlank private String bookTags;

    private MultipartFile[] bookImages;
}
