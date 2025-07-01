package shop.wannab.frontservice.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.book.client.AdminBookClient;
import shop.wannab.frontservice.book.client.BookClient;
import shop.wannab.frontservice.book.client.response.AdminBookListDto;
import shop.wannab.frontservice.book.client.response.BookDetailDto;
import shop.wannab.frontservice.global.response.ApiResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final AdminBookClient adminBookClient;
    private final BookClient bookClient;

    public List<BookDetailDto> getBooks(){
        ApiResponse<AdminBookListDto> response = adminBookClient.getBookList(0,10);
        return response.getData().getContent();
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
