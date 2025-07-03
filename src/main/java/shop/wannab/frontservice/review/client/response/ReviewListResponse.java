package shop.wannab.frontservice.review.client.response;

import shop.wannab.frontservice.global.response.PageableInfo;
import shop.wannab.frontservice.global.response.SortInfo;

import java.util.List;

public record ReviewListResponse(
    List<ReviewResponse> content,
    PageableInfo pageable,
    boolean last,
    int totalElements,
    int totalPages,
    boolean first,
    int size,
    int number,
    SortInfo sort,
    int numberOfElements,
    boolean empty
) {
}
