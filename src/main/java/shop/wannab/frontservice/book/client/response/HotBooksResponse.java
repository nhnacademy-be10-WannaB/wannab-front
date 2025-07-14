package shop.wannab.frontservice.book.client.response;

import java.util.List;

public record HotBooksResponse (
        Long bookId,
        String title,
        String description,
        List<String> imageUrls
){
}
