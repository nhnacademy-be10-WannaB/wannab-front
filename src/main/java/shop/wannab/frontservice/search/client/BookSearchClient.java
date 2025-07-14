package shop.wannab.frontservice.search.client;

import java.util.List;
import java.util.Set;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.wannab.frontservice.search.domain.BookSearchField;
import shop.wannab.frontservice.search.dto.response.SearchResultWithSectionResponse;

@FeignClient(name = "gateway", url = "${gateway.api.url}", path = "/book-service", contextId = "bookSearchClient")
public interface BookSearchClient {

    @GetMapping("/api/books/search/total")
    List<SearchResultWithSectionResponse> searchMulti(@RequestParam("keyword") String keyword,
                                                      @RequestParam("field") Set<BookSearchField> fields);

}
