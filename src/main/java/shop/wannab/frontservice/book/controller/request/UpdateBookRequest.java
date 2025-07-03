package shop.wannab.frontservice.book.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateBookRequest {

    @NotBlank private String title;
    private String bookChapter;
    @NotBlank private String description;
    @NotBlank private String category;
    @NotBlank private String author;
    @NotBlank private String publisher;
    @NotNull private LocalDate publicationDate;
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

    @Builder.Default
    private List<String> removeImages = new ArrayList<>();

    @Builder.Default
    private List<String> originalImageUrls = new ArrayList<>();

    private MultipartFile[] bookImages;
}
