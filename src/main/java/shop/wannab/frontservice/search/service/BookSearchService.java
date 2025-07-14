package shop.wannab.frontservice.search.service;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.search.client.BookSearchClient;
import shop.wannab.frontservice.search.domain.BookSearchField;
import shop.wannab.frontservice.search.dto.response.SearchResultWithSectionResponse;

@Service
@RequiredArgsConstructor
public class BookSearchService {

    private final BookSearchClient bookSearchClient;

    public List<SearchResultWithSectionResponse> searchTotalBook(String keyword, Set<BookSearchField> fields){
        return bookSearchClient.searchMulti(keyword, fields);
    }

}
