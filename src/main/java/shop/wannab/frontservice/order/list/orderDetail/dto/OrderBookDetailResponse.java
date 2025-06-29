package shop.wannab.frontservice.order.list.orderDetail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 주문 상세 페이지에서 보여줄 도서에 대한 정보를 담는 dto
 */
@Data
@AllArgsConstructor
public class OrderBookDetailResponse {
    private final Long bookId;
    private final String title;
    private final int quantity;

    /**
     * 도서마다 총가격
     * (도서개당가격 * quantity)
     */
    private final int bookTotalPrice;
    private final String thumbnailUrl;
}
