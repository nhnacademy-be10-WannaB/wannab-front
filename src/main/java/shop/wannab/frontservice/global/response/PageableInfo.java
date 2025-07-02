package shop.wannab.frontservice.global.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableInfo {
    private int pageNumber;
    private int pageSize;
    private SortInfo sort;
    private long offset;
    private boolean unpaged;
    private boolean paged;
}
