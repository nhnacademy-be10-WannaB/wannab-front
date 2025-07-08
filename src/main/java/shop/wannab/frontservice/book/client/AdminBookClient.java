package shop.wannab.frontservice.book.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import shop.wannab.frontservice.book.client.request.CreateBookFeignRequest;
import shop.wannab.frontservice.book.client.request.SearchRequest;
import shop.wannab.frontservice.book.client.request.UpdateBookFeignRequest;
import shop.wannab.frontservice.book.client.response.AdminBookListResponse;
import shop.wannab.frontservice.book.client.response.SearchResponse;
import shop.wannab.frontservice.book.controller.request.AladinBookRequest;
import shop.wannab.frontservice.couponpolicy.BookCouponInfoDto;
import shop.wannab.frontservice.global.response.ApiResponse;


/**
 * Book Service 에 요청을 보내는 FeignClient
 *
 * @author hunmin
 */
@FeignClient(name = "gateway", url = "${gateway.api.url}", path = "/book-service", contextId = "adminBookClient")
public interface AdminBookClient {

    @PostMapping("/api/admin/aladin/books/search")
    SearchResponse searchFromBookService(@RequestBody SearchRequest request);

    @PostMapping("/api/admin/aladin/books")
    ResponseEntity<Void> createdAladinBook(@RequestBody AladinBookRequest request);

    @GetMapping("/api/admin/books")
    ApiResponse<AdminBookListResponse> getBookList(@RequestParam("page") int page,
                                                   @RequestParam("size") int size);

    @PostMapping("/api/admin/books")
    ResponseEntity<ApiResponse<Void>> createBook(@RequestBody CreateBookFeignRequest request);

    @PutMapping("/api/admin/books/{bookId}")
    ResponseEntity<ApiResponse<Void>> updateBook(@PathVariable("bookId")Long bookId ,
                                                 @RequestBody UpdateBookFeignRequest request);
    @DeleteMapping("/api/admin/books/{bookId}")
    ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable("bookId")Long bookId);


    //도서 쿠폰 전용 정보
    @GetMapping("/api/admin/book-coupon")
    Page<BookCouponInfoDto> getBookCouponInfoList(
            @RequestParam("query") String query,
            @RequestParam("page") int page,
            @RequestParam("size") int size);
  
}
