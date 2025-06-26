package shop.wannab.frontservice.book.controller.response;

import java.time.LocalDate;
import java.util.List;

public record BookItem(
        String title,
        String category,
        List<String> author,
        List<String> publisher,
        LocalDate publishedDate,
        String isbn,
        Integer price,
        String description,
        String thumbnail
) {
}
