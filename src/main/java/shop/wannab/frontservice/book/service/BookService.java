package shop.wannab.frontservice.book.service;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.book.client.AdminBookClient;
import shop.wannab.frontservice.book.client.BookClient;
import shop.wannab.frontservice.book.client.response.AdminBookListResponse;
import shop.wannab.frontservice.book.client.response.BookDetailResponse;
import shop.wannab.frontservice.global.response.ApiResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final AdminBookClient adminBookClient;
    private final BookClient bookClient;

      
    public AdminBookListResponse getBooks(String sort){
        ApiResponse<AdminBookListResponse> response = adminBookClient.getBookList(0, 10, sort);
        return response.data();
    }

    public BookDetailResponse getBookDetail(Long bookId){
        ApiResponse<BookDetailResponse> bookDetailDataApiResponse = bookClient.getBookDetail(bookId);
        return bookDetailDataApiResponse.data();
    }

    public Boolean getBookLiked(Long bookId, String accessToken){
        Boolean bookLiked;
        if (accessToken == null){
            bookLiked = null;
        }else {
            ApiResponse<Boolean> isBookLikedResponse = bookClient.getBookLiked(bookId);
            bookLiked = isBookLikedResponse.data();
        }
        return bookLiked;
    }

    public void createBookLike(Long bookId){
        bookClient.createBookLike(bookId);
    }

    public void deleteBookLike(Long bookId){
        bookClient.deleteBookLike(bookId);
    }

    public AdminBookListResponse searchBooks(Long categoryId,int page, int size, String sort){
        ApiResponse<AdminBookListResponse> response = bookClient.searchBooks(categoryId,page, size, sort);
        return response.data();

    }
}
