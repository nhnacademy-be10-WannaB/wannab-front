package shop.wannab.frontservice.book.client.response;

import java.time.LocalDate;
import java.util.List;

public record BookDetailResponse(
    Long bookId,
    String title,
    String description,
    LocalDate publicationDate,
    int originPrice,
    int salesPrice,
    String isbn,
    int stock,
    String bookChapter,
    boolean status,
    List<String> authorNames,
    List<String> publisherNames,
    List<String> tagNames,
    List<String> imageUrls,
    String categoryNames
) {
}
