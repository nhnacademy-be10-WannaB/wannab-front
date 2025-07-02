package shop.wannab.frontservice.order.list.orderDetail.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.wannab.frontservice.order.list.ordersManagement.dto.OrderStatus;

/**
 * 주문 상세내역에서 보여줄 데이터들을 담는 Dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {

    private List<OrderBookDetailResponse> books;

    /**
     * 주문 번호
     * (일단 order ID 사용)
     */
    private Long orderNumber;

    /**
     * 주문 일시
     */
    private LocalDateTime orderAt;

    /**
     * 주문 상태
     */
    private OrderStatus orderStatus;

    /**
     * 총 주문 금액
     */
    private int totalPrice;
}