package shop.wannab.frontservice.book.client.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BookDetailDto {
    private Long bookId;
    private String title;
    private String description;
    private LocalDate publicationDate;
    private int originPrice;
    private int salesPrice;
    private String isbn;
    private int stock;
    private String bookChapter;
    private boolean status;
    private List<String> authorNames;
    private List<String> publisherNames;
    private List<String> tagNames;
    private List<String> imageUrls;
}
