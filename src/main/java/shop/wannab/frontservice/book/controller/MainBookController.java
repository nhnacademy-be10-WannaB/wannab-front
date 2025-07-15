package shop.wannab.frontservice.book.controller;

import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.wannab.frontservice.book.client.response.AdminBookListResponse;
import shop.wannab.frontservice.book.client.response.BookDetailResponse;
import shop.wannab.frontservice.book.client.response.BookLikeListResponse;
import shop.wannab.frontservice.book.client.response.HotBooksResponse;
import shop.wannab.frontservice.book.service.BookService;
import shop.wannab.frontservice.category.service.CategoryService;
import shop.wannab.frontservice.couponpolicy.client.CouponApiClient;
import shop.wannab.frontservice.couponpolicy.dto.IssuableCouponDto;
import shop.wannab.frontservice.review.client.response.ReviewListResponse;
import shop.wannab.frontservice.review.service.ReviewService;
import shop.wannab.frontservice.user.dto.UserPageResponse;
import shop.wannab.frontservice.user.model.UserViewModel;
import shop.wannab.frontservice.user.service.UserService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainBookController {

    private final BookService bookService;
    private final ReviewService reviewService;
    private final CategoryService categoryService;
    private final CouponApiClient couponApiClient;
    private final UserService userService;

    @GetMapping("/")
    public String mainPage(Model model){
        AdminBookListResponse newBooks = bookService.getBooks("publicationDate,asc");
        model.addAttribute("newBooks", newBooks.content());

        List<HotBooksResponse> hotBooks = bookService.getHotBooks();
        model.addAttribute("hotBooks", hotBooks);

        AdminBookListResponse recommendBooks = bookService.getBooks("stock,desc");
        model.addAttribute("recommendBooks", recommendBooks.content());

        model.addAttribute("categories",categoryService.getCategoryHierarchy());

        return "user/main";
    }

    @GetMapping("/main-book-detail/{bookId}")
    public  String bookDetail(@PathVariable("bookId") Long bookId,
                              @CookieValue(value = "access_token", required = false) String accessToken,
                              Model model){

        BookDetailResponse book = bookService.getBookDetail(bookId);
        String joinedAuthors = String.join(" | ", book.authorNames());
        String joinedPublishers = String.join(" | ", book.publisherNames());
        model.addAttribute("authorName", joinedAuthors);
        model.addAttribute("joinedPublishers", joinedPublishers);
        model.addAttribute("book",book);

        Boolean bookLiked = bookService.getBookLiked(bookId,accessToken);
        model.addAttribute("bookLiked",bookLiked);
        model.addAttribute("categories",categoryService.getCategoryHierarchy());

        ReviewListResponse bookReviews = reviewService.getBookReviews(bookId);
        model.addAttribute("bookReviews",bookReviews.content());
        model.addAttribute("bookReviewCount",bookReviews.totalElements());

        Double bookReviewAverage = reviewService.getBookReviewsAverage(bookId);
        model.addAttribute("bookReviewAverage",bookReviewAverage);

        List<IssuableCouponDto> couponList;
        try {
            couponList = couponApiClient.getIssuableCoupons(bookId);
        } catch (FeignException e){
            log.error("Coupon Service로부터 응답을 받을 수 없습니다");
            couponList = Collections.emptyList();
        }
        model.addAttribute("coupons", couponList);

        return "user/main-book-detail";
    }

    @PostMapping("/main-book-detail/{bookId}/like")
    public String createBookLike(@PathVariable("bookId") Long bookId){
        bookService.createBookLike(bookId);
        return "redirect:/main-book-detail/"+bookId;
    }

    @DeleteMapping("/main-book-detail/{bookId}/unlike")
    public String deleteBookLike(@PathVariable("bookId") Long bookId){
        bookService.deleteBookLike(bookId);
        return "redirect:/main-book-detail/"+bookId;
    }

    @PostMapping("/main-book-detail/{bookId}")
    public String mainBookDetail(@PathVariable("bookId") Long bookId,@RequestParam Long couponPolicyId) {
        couponApiClient.issueCustomCoupon(couponPolicyId);
        return "redirect:/main-book-detail/" + bookId;
    }
      
    @GetMapping("/books/search")
    public String searchBooks(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "bookId,desc" ) String sort,
                              @RequestParam String categoryName,
                              @RequestParam Long categoryId,
                              Model model){
        AdminBookListResponse response = bookService.searchBooks(categoryId,page,size,sort);

        model.addAttribute("books", response.content());
        model.addAttribute("totalPages", response.totalPages());
        model.addAttribute("currentPage", response.number());
        model.addAttribute("totalElements", response.totalElements());
        model.addAttribute("size", response.size());

        Map<String, String> sortNameMap = Map.of(
                "bookId,desc","정렬 기준 선택",
                "title,asc", "이름 오름차순",
                "title,desc", "이름 내림차순",
                "originPrice,asc", "가격 오름차순",
                "originPrice,desc", "가격 내림차순",
                "publicationDate,desc", "최신순",
                "publicationDate,asc", "오래된순"
        );

        String sortName = sortNameMap.getOrDefault(sort, "정렬 기준 선택");
        model.addAttribute("categories",categoryService.getCategoryHierarchy());

        model.addAttribute("categoryName",categoryName);
        model.addAttribute("sort", sort);
        model.addAttribute("sortName", sortName);
        model.addAttribute("categoryId", categoryId);

        int totalPages = response.totalPages();
        int currentPage = response.number();
        int visibleRange = 5;

        int startPage = Math.max(0, currentPage - (visibleRange / 2));
        int endPage = Math.min(totalPages - 1, startPage + visibleRange - 1);

        if (endPage - startPage < visibleRange - 1) {
            startPage = Math.max(0, endPage - visibleRange + 1);
        }

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("prevPage", currentPage > 0 ? currentPage - 1 : 0);
        model.addAttribute("nextPage", currentPage < totalPages - 1 ? currentPage + 1 : totalPages - 1);

        return "user/main-search";
    }


    @GetMapping("/user/mypage-liked")
    public String mypageLiked(HttpServletRequest request, Model model) {
        UserPageResponse user = userService.readUser();

        UserViewModel viewModel = UserViewModel.builder()
                .id(user.username())
                .password(user.password())
                .phone(user.phone())
                .birth(user.birth())
                .nickname(user.nickname())
                .email(user.email())
                .name(user.name())
                .points(user.points())
                .grade(user.grade())
                .build();

        model.addAttribute("user", viewModel);
        model.addAttribute("currentUri", request.getRequestURI());

        BookLikeListResponse response = bookService.getLikedBooks();
        model.addAttribute("likedBooks",response.content());

        return "user/mypage-liked";
    }

    @DeleteMapping("/user/mypage-liked/{bookId}/unlike")
    public String mypageUnLiked(@PathVariable("bookId")Long bookId){
        bookService.deleteBookLike(bookId);
        return "redirect:/user/mypage-liked";
    }
}
