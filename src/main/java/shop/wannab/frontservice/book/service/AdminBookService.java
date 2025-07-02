package shop.wannab.frontservice.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.book.client.AdminBookClient;
import shop.wannab.frontservice.book.client.request.SearchRequest;
import shop.wannab.frontservice.book.client.response.AdminBookListResponse;
import shop.wannab.frontservice.book.client.response.BookDetailResponse;
import shop.wannab.frontservice.book.client.response.SearchResponse;
import shop.wannab.frontservice.book.controller.request.CreateBookRequest;
import shop.wannab.frontservice.book.controller.response.SearchBookResponse;
import shop.wannab.frontservice.global.response.ApiResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminBookService {

    private final AdminBookClient adminBookClient;

    public SearchBookResponse searchBooks(SearchRequest searchRequest) {
        SearchResponse searchResponse = adminBookClient.searchFromBookService(searchRequest);
        return SearchBookResponse.from(searchResponse);
    }

    public AdminBookListResponse getBooks(int page, int size){
        ApiResponse<AdminBookListResponse> response = adminBookClient.getBookList(page, size);
        return response.data();
    }

    public void CreateBook(CreateBookRequest request){


    }
}