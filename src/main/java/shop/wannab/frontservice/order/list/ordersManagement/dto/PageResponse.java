package shop.wannab.frontservice.order.list.ordersManagement.dto;

import java.util.List;
import lombok.Data;

@Data
public class PageResponse<T> {

    /**
     * 들어오는 값들
     */
    private List<T> content;

    /**
     * 현재 페이지
     */
    private int number;

    /**
     * 한 페이지에 들어가는 주문목록 수
     */
    private int size;

    /**
     * 전체 페이지 수
     */
    private int totalPages;

    /**
     * 전체 데이터 수
     */
    private long totalElements;

    /**
     * 현재 페이지가 첫번째 페이지인지
     */
    private boolean first;

    /**
     * 현재 페이지가 마지막 페이지인지
     */
    private boolean last;
}
