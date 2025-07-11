package shop.wannab.frontservice.book.client.response;

import java.util.List;

public record LikedBookResponse (
        Long bookId,
        String title,
        List<String> imageUrl,
        List<String> authors,
        boolean liked
){
    public String joinAuthors() {
        return String.join(" | ", authors);
    }
}
