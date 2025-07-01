package shop.wannab.frontservice.book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import shop.wannab.frontservice.book.client.response.BookDetailDto;
import shop.wannab.frontservice.book.service.BookService;
import shop.wannab.frontservice.review.client.response.ReviewDto;
import shop.wannab.frontservice.review.service.ReviewService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainBookController {

    private final BookService bookService;
    private final ReviewService reviewService;

    @GetMapping("/")
    public String mainPage(Model model){
        List<BookDetailDto> books = bookService.getBooks();
        model.addAttribute("books", books);
        return "user/main";
    }

    @GetMapping("/main-book-detail/{bookId}")
    public  String bookDetail(@PathVariable("bookId") Long bookId,
                              @CookieValue(value = "access_token", required = false) String accessToken,
                              Model model){

        BookDetailDto book = bookService.getBookDetail(bookId);
        String joinedAuthors = String.join(" | ", book.getAuthorNames());
        model.addAttribute("authorName", joinedAuthors);
        model.addAttribute("book",book);

        Boolean bookLiked = bookService.getBookLiked(bookId,accessToken);
        model.addAttribute("bookLiked",bookLiked);

        List<ReviewDto> bookReviews = reviewService.getBookReviews(bookId);
        model.addAttribute("bookReviews",bookReviews);

        return "user/main-book-detail";
    }

}
