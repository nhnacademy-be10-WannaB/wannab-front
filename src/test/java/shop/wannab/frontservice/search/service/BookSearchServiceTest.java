package shop.wannab.frontservice.search.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.wannab.frontservice.search.client.BookSearchClient;
import shop.wannab.frontservice.search.domain.BookSearchField;
import shop.wannab.frontservice.search.dto.response.SearchResultWithSectionResponse;

@ExtendWith(MockitoExtension.class)
class BookSearchServiceTest {

    @Mock
    private BookSearchClient bookSearchClient;

    @InjectMocks
    private BookSearchService bookSearchService;

    @Test
    @DisplayName("전체 도서 검색")
    void searchTotalBook() {
        List<SearchResultWithSectionResponse> expectedResponse = Collections.emptyList();
        given(bookSearchClient.searchMulti(anyString(), anySet())).willReturn(expectedResponse);

        List<SearchResultWithSectionResponse> result = bookSearchService.searchTotalBook("keyword", Set.of(BookSearchField.TITLE));

        assertEquals(expectedResponse, result);
        verify(bookSearchClient).searchMulti(anyString(), anySet());
    }
}
