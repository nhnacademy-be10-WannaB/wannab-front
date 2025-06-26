package shop.wannab.frontservice.book.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchResponse(
        String title,
        Integer totalResults,
        Integer startIndex,
        Integer ItemsPerPage,
        String query,
        String searchCategoryId,
        String searchCategoryName,

        @JsonProperty("item")
        List<SearchBookItem> items
) {
}
