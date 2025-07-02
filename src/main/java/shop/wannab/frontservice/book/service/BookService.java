package shop.wannab.frontservice.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.book.client.AdminBookClient;
import shop.wannab.frontservice.book.client.BookClient;
import shop.wannab.frontservice.book.client.response.AdminBookListResponse;
import shop.wannab.frontservice.book.client.response.BookDetailResponse;
import shop.wannab.frontservice.global.response.ApiResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final AdminBookClient adminBookClient;
    private final BookClient bookClient;

    public List<BookDetailResponse> getBooks(){
        ApiResponse<AdminBookListResponse> response = adminBookClient.getBookList(0,10);
        return response.data().content();
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
}
