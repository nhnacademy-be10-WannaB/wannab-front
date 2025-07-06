package shop.wannab.frontservice.book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import shop.wannab.frontservice.book.client.response.BookDetailResponse;
import shop.wannab.frontservice.book.service.BookService;
import shop.wannab.frontservice.category.service.CategoryService;
import shop.wannab.frontservice.review.client.response.ReviewResponse;
import shop.wannab.frontservice.review.service.ReviewService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainBookController {

    private final BookService bookService;
    private final ReviewService reviewService;
    private final CategoryService categoryService;

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
        List<ReviewResponse> bookReviews = reviewService.getBookReviews(bookId);
        model.addAttribute("bookReviews",bookReviews);

        return "user/main-book-detail";
    }

}
