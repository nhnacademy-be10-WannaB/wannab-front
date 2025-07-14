package shop.wannab.frontservice.search.dto.response;

import java.util.List;

public record SearchResultWithSectionResponse(
        String field,
        String label,
        Long total,
        List<BookSearchResult> results
) {
}
