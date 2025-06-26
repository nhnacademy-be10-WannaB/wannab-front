package shop.wannab.frontservice.book.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import shop.wannab.frontservice.book.client.request.SearchRequest;
import shop.wannab.frontservice.book.client.response.SearchResponse;

/**
 * Book Service 에 요청을 보내는 FeignClient
 *
 * @author hunmin
 */
@FeignClient(name = "BOOK-SERVICE", url = "${gateway.url}", contextId = "adminBookClient")
public interface AdminBookClient {

    @PostMapping("/api/admin/aladin/books/search")
    SearchResponse searchFromBookService(SearchRequest request);

}
