package shop.wannab.frontservice.book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.wannab.frontservice.book.client.response.AdminBookListResponse;
import shop.wannab.frontservice.book.client.response.BookDetailResponse;
import shop.wannab.frontservice.book.service.BookService;
import shop.wannab.frontservice.category.service.CategoryService;
import shop.wannab.frontservice.couponpolicy.client.CouponApiClient;
import shop.wannab.frontservice.couponpolicy.dto.IssuableCouponDto;
import shop.wannab.frontservice.review.client.response.ReviewListResponse;
import shop.wannab.frontservice.review.service.ReviewService;

import java.util.List;
import java.util.Map;

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
}
