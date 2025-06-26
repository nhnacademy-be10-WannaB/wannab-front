package shop.wannab.frontservice.book.controller.response;

import java.util.List;
import shop.wannab.frontservice.book.client.response.SearchBookItem;
import shop.wannab.frontservice.book.client.response.SearchResponse;

public record SearchBookResponse(
        String title,
        Integer totalResults,
        Integer startIndex,
        Integer ItemsPerPage,
        String query,
        List<BookItem> items
) {
    public static SearchBookResponse from(SearchResponse response) {
        List<BookItem> bookItems = response.items().stream()
                .map(SearchBookItem::toBookItem)
                .toList();

        return new SearchBookResponse(
                response.title(),
                response.totalResults(),
                response.startIndex(),
                response.ItemsPerPage(),
                response.query(),
                bookItems
        );
    }
}
