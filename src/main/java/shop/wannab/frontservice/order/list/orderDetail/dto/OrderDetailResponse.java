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
    private Long orderId;

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

    /**
     * 배송비
     */
    private int shippingFee;

    /**
     * 총 할인가격
     */
    private int totalDiscount;

    /**
     * 총 포장지가격
     */
    private int totalWrappinpPrice;

    /**
     * 주문자 이름
     */
    private String name;
}