package shop.wannab.frontservice.book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.wannab.frontservice.book.client.response.BookDetailResponse;
import shop.wannab.frontservice.book.service.BookService;
import shop.wannab.frontservice.category.service.CategoryService;
import shop.wannab.frontservice.couponpolicy.CouponApiClient;
import shop.wannab.frontservice.couponpolicy.IssuableCouponDto;
import shop.wannab.frontservice.review.client.response.ReviewListResponse;
import shop.wannab.frontservice.review.client.response.ReviewResponse;
import shop.wannab.frontservice.review.service.ReviewService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainBookController {

    private final BookService bookService;
    private final ReviewService reviewService;
    private final CategoryService categoryService;
    private final CouponApiClient couponApiClient;

    @GetMapping("/")
    public String mainPage(Model model){
        List<BookDetailResponse> books = bookService.getBooks();

        model.addAttribute("categories",categoryService.getCategoryHierarchy());

        model.addAttribute("books", books);
        return "user/main";
    }

    @GetMapping("/main-book-detail/{bookId}")
    public  String bookDetail(@PathVariable("bookId") Long bookId,
                              @CookieValue(value = "access_token", required = false) String accessToken,
                              Model model){

        BookDetailResponse book = bookService.getBookDetail(bookId);
        String joinedAuthors = String.join(" | ", book.authorNames());
        model.addAttribute("authorName", joinedAuthors);
        model.addAttribute("book",book);

        Boolean bookLiked = bookService.getBookLiked(bookId,accessToken);
        model.addAttribute("bookLiked",bookLiked);
        model.addAttribute("categories",categoryService.getCategoryHierarchy());

        ReviewListResponse bookReviews = reviewService.getBookReviews(bookId);
        model.addAttribute("bookReviews",bookReviews.content());
        model.addAttribute("bookReviewCount",bookReviews.totalElements());

        Double bookReviewAverage = reviewService.getBookReviewsAverage(bookId);
        model.addAttribute("bookReviewAverage",bookReviewAverage);

        List<IssuableCouponDto> couponList = couponApiClient.getIssuableCoupons(bookId);
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
        return "redirect:/main-book-detail/"+bookId;
    }
}
