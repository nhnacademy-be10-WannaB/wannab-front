package shop.wannab.frontservice.book.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.wannab.frontservice.book.client.request.CreateBookFeignRequest;
import shop.wannab.frontservice.book.client.request.SearchRequest;
import shop.wannab.frontservice.book.client.request.UpdateBookFeignRequest;
import shop.wannab.frontservice.book.client.response.AdminBookListResponse;
import shop.wannab.frontservice.book.client.response.SearchResponse;
import shop.wannab.frontservice.book.controller.request.AladinBookRequest;

import shop.wannab.frontservice.global.response.ApiResponse;


/**
 * Book Service 에 요청을 보내는 FeignClient
 *
 * @author hunmin
 */
@FeignClient(name = "gateway", url = "${gateway.api.url}", path = "/book-service", contextId = "adminBookClient")
public interface AdminBookClient {

    @PostMapping("/api/admin/aladin/books/search")
    SearchResponse searchFromBookService(SearchRequest request);

    @PostMapping("/api/admin/aladin/books")
    ResponseEntity<Void> createdAladinBook(AladinBookRequest request);

    @GetMapping("/api/admin/books")
    ApiResponse<AdminBookListResponse> getBookList(@RequestParam("page") int page,
                                                   @RequestParam("size") int size);

    @PostMapping("/api/admin/books")
    ResponseEntity<ApiResponse<Void>> createBook(CreateBookFeignRequest request);

    @PutMapping("/api/admin/books/{bookId}")
    ResponseEntity<ApiResponse<Void>> updateBook(@PathVariable("bookId")Long bookId ,
                                                 UpdateBookFeignRequest request);
    @DeleteMapping("/api/admin/books/{bookId}")
    ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable("bookId")Long bookId);

}
