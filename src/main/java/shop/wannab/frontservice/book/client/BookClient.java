package shop.wannab.frontservice.book.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import shop.wannab.frontservice.book.client.response.BookDetailDto;
import shop.wannab.frontservice.global.response.ApiResponse;

@FeignClient(name = "gateway", url = "${gateway.api.url}", path = "/book-service", contextId = "bookClient")
public interface BookClient {
    @GetMapping("/api/books/{bookId}")
    ApiResponse<BookDetailDto> getBookDetail(@PathVariable("bookId") Long bookId);

    @GetMapping("/api/books/{bookId}/likes")
    ApiResponse<Boolean> getBookLiked(@PathVariable("bookId") Long bookId);


}

