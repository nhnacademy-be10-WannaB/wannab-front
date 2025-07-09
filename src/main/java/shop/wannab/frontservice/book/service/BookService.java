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

      
    public List<BookDetailResponse> getBooks(){
        try{
            ApiResponse<AdminBookListResponse> response = adminBookClient.getBookList(0,10,"bookId,desc");
            return response.data().content();
        } catch (Exception e){
            log.error("[Book Service] 도서 목록 조회 실패, 도서 서비스가 실행되지 않고 있을 수 있습니다");
            return Collections.emptyList();
        }
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
}
