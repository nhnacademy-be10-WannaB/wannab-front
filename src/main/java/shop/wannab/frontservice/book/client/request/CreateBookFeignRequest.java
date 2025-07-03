package shop.wannab.frontservice.book.client.request;

import java.time.LocalDate;

public record CreateBookFeignRequest(
        String title,
        String description,
        LocalDate publicationDate,
        Integer originPrice,
        Integer salesPrice,
        Integer stock,
        String bookChapter,
        String isbn,
        boolean status,
        String categories,
        String authors,
        String publishers,
        String bookImages,
        String bookTags
) {

}
