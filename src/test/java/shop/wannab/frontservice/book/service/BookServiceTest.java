package shop.wannab.frontservice.book.service;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.wannab.frontservice.auth.service.AuthService;
import shop.wannab.frontservice.book.client.AdminBookClient;
import shop.wannab.frontservice.book.client.BookClient;
import shop.wannab.frontservice.book.client.response.AdminBookListResponse;
import shop.wannab.frontservice.book.client.response.BookDetailResponse;
import shop.wannab.frontservice.book.client.response.BookLikeListResponse;
import shop.wannab.frontservice.book.client.response.HotBooksResponse;
import shop.wannab.frontservice.global.response.ApiResponse;
import shop.wannab.frontservice.global.response.PageableInfo;
import shop.wannab.frontservice.global.response.SortInfo;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private AdminBookClient adminBookClient;

    @Mock
    private BookClient bookClient;

    @Mock
    private AuthService authService;

    @InjectMocks
    private BookService bookService;

    private AdminBookListResponse adminBookListResponse;
    private BookDetailResponse bookDetailResponse;
    private BookLikeListResponse bookLikeListResponse;

    @BeforeEach
    void setUp() {
        adminBookListResponse = new AdminBookListResponse(Collections.emptyList(),
            new PageableInfo(1, 1, new SortInfo(true, true, true), 1, true, true), true, 1, 1, true, 1, 1,
            new SortInfo(true, true, true), 1, true);
        bookDetailResponse = new BookDetailResponse(1L, "test", "test", LocalDate.now(), 1, 1, "test", 1, "test",
            true, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(),
            Collections.emptyList(), "test");
        bookLikeListResponse = new BookLikeListResponse(Collections.emptyList(),
            new PageableInfo(1, 1, new SortInfo(true, true, true), 1, true, true), true, 1, 1, true, 1, 1,
            new SortInfo(true, true, true), 1, true);
    }

    @Test
    @DisplayName("도서 목록 조회")
    void getBooks() {
        given(adminBookClient.getBookList(anyInt(), anyInt(), anyString())).willReturn(new ApiResponse<>("success", adminBookListResponse, null));

        AdminBookListResponse response = bookService.getBooks("test");

        verify(adminBookClient).getBookList(anyInt(), anyInt(), anyString());
    }

    @Test
    @DisplayName("인기 도서 목록 조회")
    void getHotBooks() {
        given(bookClient.getHotBooks()).willReturn(new ApiResponse<>("success", Collections.emptyList(), null));

        List<HotBooksResponse> response = bookService.getHotBooks();

        verify(bookClient).getHotBooks();
    }

    @Test
    @DisplayName("도서 상세 조회")
    void getBookDetail() {
        given(bookClient.getBookDetail(anyLong())).willReturn(new ApiResponse<>("success", bookDetailResponse, null));

        BookDetailResponse response = bookService.getBookDetail(1L);

        verify(bookClient).getBookDetail(anyLong());
    }

    @Test
    @DisplayName("도서 좋아요 여부 조회 - 로그인 상태")
    void getBookLiked_logined() {
        given(authService.isLogined()).willReturn(true);
        given(bookClient.getBookLiked(anyLong())).willReturn(new ApiResponse<>("success", true, null));

        Boolean response = bookService.getBookLiked(1L);

        verify(authService).isLogined();
        verify(bookClient).getBookLiked(anyLong());
    }

    @Test
    @DisplayName("도서 좋아요 여부 조회 - 비로그인 상태")
    void getBookLiked_notLogined() {
        given(authService.isLogined()).willReturn(false);

        Boolean response = bookService.getBookLiked(1L);

        verify(authService).isLogined();
    }

    @Test
    @DisplayName("도서 좋아요 생성")
    void createBookLike() {
        given(bookClient.createBookLike(anyLong())).willReturn(new ApiResponse<>("success", null, null));

        bookService.createBookLike(1L);

        verify(bookClient).createBookLike(anyLong());
    }

    @Test
    @DisplayName("도서 좋아요 삭제")
    void deleteBookLike() {
        given(bookClient.deleteBookLike(anyLong())).willReturn(new ApiResponse<>("success", null, null));

        bookService.deleteBookLike(1L);

        verify(bookClient).deleteBookLike(anyLong());
    }

    @Test
    @DisplayName("카테고리별 도서 검색")
    void searchBooks() {
        given(bookClient.searchBooks(anyLong(), anyInt(), anyInt(), anyString())).willReturn(new ApiResponse<>("success", adminBookListResponse, null));

        AdminBookListResponse response = bookService.searchBooks(1L, 0, 10, "test");

        verify(bookClient).searchBooks(anyLong(), anyInt(), anyInt(), anyString());
    }

    @Test
    @DisplayName("좋아요한 도서 목록 조회")
    void getLikedBooks() {
        given(bookClient.getLikedBooks()).willReturn(new ApiResponse<>("success", bookLikeListResponse, null));

        BookLikeListResponse response = bookService.getLikedBooks();

        verify(bookClient).getLikedBooks();
    }
}
