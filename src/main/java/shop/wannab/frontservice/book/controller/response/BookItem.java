package shop.wannab.frontservice.book.controller.response;

import java.time.LocalDate;
import java.util.List;

public record BookItem(
        String title,
        String category,
        List<String> authors,
        List<String> publishers,
        LocalDate publishedDate,
        String isbn,
        Integer price,
        String description,
        String thumbnail
) {
}
