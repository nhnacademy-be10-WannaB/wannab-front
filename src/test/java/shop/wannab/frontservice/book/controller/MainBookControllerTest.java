package shop.wannab.frontservice.book.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import shop.wannab.frontservice.auth.service.AuthClient;
import shop.wannab.frontservice.book.client.response.AdminBookListResponse;
import shop.wannab.frontservice.book.client.response.BookDetailResponse;
import shop.wannab.frontservice.book.client.response.BookLikeListResponse;
import shop.wannab.frontservice.book.service.BookService;
import shop.wannab.frontservice.category.service.CategoryService;
import shop.wannab.frontservice.couponpolicy.client.CouponApiClient;
import shop.wannab.frontservice.global.filter.JwtAuthorizationFilter;
import shop.wannab.frontservice.global.response.PageableInfo;
import shop.wannab.frontservice.global.response.SortInfo;
import shop.wannab.frontservice.review.client.response.ReviewListResponse;
import shop.wannab.frontservice.review.service.ReviewService;
import shop.wannab.frontservice.user.dto.UserPageResponse;
import shop.wannab.frontservice.user.service.UserService;

@ActiveProfiles("ci")
@WebMvcTest(controllers = MainBookController.class,
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthorizationFilter.class)
    })
class MainBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private CouponApiClient couponApiClient;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthClient authClient;

    private AdminBookListResponse adminBookListResponse;
    private BookDetailResponse bookDetailResponse;
    private ReviewListResponse reviewListResponse;
    private BookLikeListResponse bookLikeListResponse;

    @BeforeEach
    void setUp() {
        adminBookListResponse = new AdminBookListResponse(Collections.emptyList(),
            new PageableInfo(1, 1, new SortInfo(true, true, true), 1, true, true), true, 1, 1, true, 1, 1,
            new SortInfo(true, true, true), 1, true);
        bookDetailResponse = new BookDetailResponse(1L, "test", "test", LocalDate.now(), 1, 1, "test", 1, "test",
            true, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(),
            Collections.emptyList(), "test");
        reviewListResponse = new ReviewListResponse(Collections.emptyList(),
            new PageableInfo(1, 1, new SortInfo(true, true, true), 1, true, true), true, 1, 1, true, 1, 1,
            new SortInfo(true, true, true), 1, true);
        bookLikeListResponse = new BookLikeListResponse(Collections.emptyList(),
            new PageableInfo(1, 1, new SortInfo(true, true, true), 1, true, true), true, 1, 1, true, 1, 1,
            new SortInfo(true, true, true), 1, true);
    }

    @Test
    @WithMockUser
    @DisplayName("메인 페이지 조회")
    void mainPage() throws Exception {
        given(bookService.getBooks(anyString())).willReturn(adminBookListResponse);
        given(bookService.getHotBooks()).willReturn(Collections.emptyList());
        given(categoryService.getCategoryHierarchy()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("public/main"));
    }

    @Test
    @WithMockUser
    @DisplayName("도서 상세 페이지 조회")
    void bookDetail() throws Exception {
        given(bookService.getBookDetail(anyLong())).willReturn(bookDetailResponse);
        given(bookService.getBookLiked(anyLong())).willReturn(true);
        given(reviewService.getBookReviews(anyLong())).willReturn(reviewListResponse);
        given(reviewService.getBookReviewsAverage(anyLong())).willReturn(5.0);
        given(couponApiClient.getIssuableCoupons(anyLong())).willReturn(Collections.emptyList());

        mockMvc.perform(get("/main-book-detail/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("public/main-book-detail"));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("도서 좋아요 생성")
    void createBookLike() throws Exception {
        doNothing().when(bookService).createBookLike(anyLong());

        mockMvc.perform(post("/main-book-detail/1/like").with(csrf()))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("도서 좋아요 삭제")
    void deleteBookLike() throws Exception {
        doNothing().when(bookService).deleteBookLike(anyLong());

        mockMvc.perform(delete("/main-book-detail/1/unlike").with(csrf()))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("쿠폰 발급")
    void mainBookDetail() throws Exception {
        doNothing().when(couponApiClient).issueCustomCoupon(anyLong());

        mockMvc.perform(post("/main-book-detail/1").with(csrf())
                .param("couponPolicyId", "1"))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    @DisplayName("도서 검색")
    void searchBooks() throws Exception {
        given(bookService.searchBooks(anyLong(), anyInt(), anyInt(), anyString())).willReturn(adminBookListResponse);
        given(categoryService.getCategoryHierarchy()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/books/search")
                .param("categoryName", "test")
                .param("categoryId", "1"))
            .andExpect(status().isOk())
            .andExpect(view().name("public/main-search"));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("마이페이지 좋아요 목록 조회")
    void mypageLiked() throws Exception {
        given(userService.readUser()).willReturn(
            new UserPageResponse("test", "test", "test", "test", LocalDate.now(), "test", "test", 1, "test"));
        given(bookService.getLikedBooks()).willReturn(bookLikeListResponse);

        mockMvc.perform(get("/user/mypage-liked"))
            .andExpect(status().isOk())
            .andExpect(view().name("user/mypage-liked"));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("마이페이지 좋아요 삭제")
    void mypageUnLiked() throws Exception {
        doNothing().when(bookService).deleteBookLike(anyLong());

        mockMvc.perform(delete("/user/mypage-liked/1/unlike").with(csrf()))
            .andExpect(status().is3xxRedirection());
    }
}
