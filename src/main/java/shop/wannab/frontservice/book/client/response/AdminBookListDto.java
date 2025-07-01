package shop.wannab.frontservice.book.client.response;

import lombok.Getter;
import lombok.Setter;
import shop.wannab.frontservice.global.response.PageableInfo;
import shop.wannab.frontservice.global.response.SortInfo;

import java.util.List;

@Getter
@Setter
public class AdminBookListDto {
    private List<BookDetailDto> content;
    private PageableInfo pageable;
    private boolean last;
    private int totalElements;
    private int totalPages;
    private boolean first;
    private int size;
    private int number;
    private SortInfo sort;
    private int numberOfElements;
    private boolean empty;
}
