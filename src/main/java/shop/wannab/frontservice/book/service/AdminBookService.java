package shop.wannab.frontservice.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.book.client.AdminBookClient;
import shop.wannab.frontservice.book.client.request.SearchRequest;
import shop.wannab.frontservice.book.client.response.SearchResponse;
import shop.wannab.frontservice.book.controller.response.SearchBookResponse;

@Service
@RequiredArgsConstructor
public class AdminBookService {

    private final AdminBookClient adminBookClient;

    public SearchBookResponse searchBooks(SearchRequest searchRequest) {
        SearchResponse searchResponse = adminBookClient.searchFromBookService(searchRequest);
        return SearchBookResponse.from(searchResponse);
    }
}
