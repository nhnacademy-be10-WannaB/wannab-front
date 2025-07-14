package shop.wannab.frontservice.search.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookSearchField {
    ALL("all", "통합 검색"),
    TITLE("title", "제목 검색"),
    DESCRIPTION("description", "설명 검색"),
    TAGS("tags", "태그 검색"),
    CATEGORIES("categories", "카테고리 검색"),
    AUTHORS("authors", "저자 검색"),
    PUBLISHERS("publishers", "출판사 검색");

    private final String fieldName;
    private final String description;
}
