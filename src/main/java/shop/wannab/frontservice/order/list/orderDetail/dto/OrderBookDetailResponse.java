package shop.wannab.frontservice.order.list.orderDetail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 주문 상세 페이지에서 보여줄 도서에 대한 정보를 담는 dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderBookDetailResponse {
    private Long bookId;
    private String title;
    private int quantity;

    /**
     * 도서마다 총가격
     * (도서개당가격 * quantity)
     */
    private int bookTotalPrice;
    private String thumbnailUrl;
}
