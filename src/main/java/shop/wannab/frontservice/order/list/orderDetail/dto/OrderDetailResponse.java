package shop.wannab.frontservice.order.list.orderDetail.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import shop.wannab.frontservice.order.list.ordersManagement.dto.OrderStatus;

/**
 * 주문 상세내역에서 보여줄 데이터들을 담는 Dto
 */
@Data
@AllArgsConstructor
public class OrderDetailResponse {

    private final List<OrderBookDetailResponse> books;

    /**
     * 주문 번호
     * (일단 order ID 사용)
     */
    private final Long orderNumber;

    /**
     * 주문 일시
     */
    private final LocalDateTime orderAt;

    /**
     * 결제 일시
     */
    private final LocalDateTime paymentAt;

    /**
     * 주문 상태
     */
    private final OrderStatus orderStatus;

    /**
     * 총 주문 금액
     */
    private final int totalPrice;
}