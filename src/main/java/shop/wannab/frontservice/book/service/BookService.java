package shop.wannab.frontservice.book.service;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.book.client.AdminBookClient;
import shop.wannab.frontservice.book.client.BookClient;
import shop.wannab.frontservice.book.client.response.AdminBookListDto;
import shop.wannab.frontservice.book.client.response.BookDetailDto;
import shop.wannab.frontservice.global.response.ApiResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final AdminBookClient adminBookClient;
    private final BookClient bookClient;

    public List<BookDetailDto> getBooks(){
        try{
            ApiResponse<AdminBookListDto> response = adminBookClient.getBookList(0,10);
            return response.getData().getContent();
        } catch (Exception e){
            log.error("[Book Service] 도서 목록 조회 실패, 도서 서비스가 실행되지 않고 있을 수 있습니다");
            return Collections.emptyList();
        }
    }

    public BookDetailDto getBookDetail(Long bookId){
        ApiResponse<BookDetailDto> bookDetailDataApiResponse = bookClient.getBookDetail(bookId);
        return bookDetailDataApiResponse.getData();
    }

    public Boolean getBookLiked(Long bookId, String accessToken){
        Boolean bookLiked;
        if (accessToken == null){
            bookLiked = null;
        }else {
            ApiResponse<Boolean> isBookLikedResponse = bookClient.getBookLiked(bookId);
            bookLiked = isBookLikedResponse.getData();
        }
        return bookLiked;
    }
}
