package shop.wannab.frontservice.order.list.ordersManagement.dto;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 주문 목록 조회시 클라이언트에게 보여줄 정보
 * (현재 페이지에서는 도서정보도 포함)
 */
@Data
@NoArgsConstructor
public class OrderLookupResponse {
    private Long orderId;

    /**
     * 주문명
     */
    private String orderName;
    /**
     * 주문 일시
     */
    private LocalDateTime orderAt;

    /**
     * 배송 상태
     */
    private OrderStatus orderStatus;

    /**
     * 출고일
     */
    private LocalDateTime shippedAt;
    private int totalPrice;

    private String thumbnailUrl;
}
