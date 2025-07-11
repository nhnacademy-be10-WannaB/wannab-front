package shop.wannab.frontservice.book.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import shop.wannab.frontservice.book.client.response.AdminBookListResponse;
import shop.wannab.frontservice.book.client.response.BookDetailResponse;
import shop.wannab.frontservice.book.client.response.BookLikeListResponse;
import shop.wannab.frontservice.global.response.ApiResponse;

@FeignClient(name = "gateway", url = "${gateway.api.url}", path = "/book-service", contextId = "bookClient")
public interface BookClient {
    @GetMapping("/api/books/{bookId}")
    ApiResponse<BookDetailResponse> getBookDetail(@PathVariable("bookId") Long bookId);

    @GetMapping("/api/books/{bookId}/likes")
    ApiResponse<Boolean> getBookLiked(@PathVariable("bookId") Long bookId);

    @PostMapping("/api/books/{bookId}/likes")
    ApiResponse<Void> createBookLike(@PathVariable("bookId") Long bookId);

    @DeleteMapping("/api/books/{bookId}/likes")
    ApiResponse<Void> deleteBookLike(@PathVariable("bookId") Long bookId);

    @GetMapping("/api/books/{categoryId}/search")
    ApiResponse<AdminBookListResponse> searchBooks (@PathVariable("categoryId")Long categoryId,
                                                    @RequestParam("page") int page,
                                                    @RequestParam("size") int size,
                                                    @RequestParam("sort") String sort
    );

    @GetMapping("/api/books/liked-books")
    ApiResponse<BookLikeListResponse> getLikedBooks();
}

