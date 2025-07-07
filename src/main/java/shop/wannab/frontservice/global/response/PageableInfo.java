package shop.wannab.frontservice.global.response;

public record PageableInfo(
    int pageNumber,
    int pageSize,
    SortInfo sort,
    long offset,
    boolean unpaged,
    boolean paged
) {
}
