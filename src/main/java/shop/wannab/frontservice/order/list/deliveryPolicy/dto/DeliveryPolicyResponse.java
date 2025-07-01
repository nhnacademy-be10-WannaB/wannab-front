package shop.wannab.frontservice.order.list.deliveryPolicy.dto;

import lombok.Data;

@Data
public class DeliveryPolicyResponse {
    /**
     * 정책 id
     */
    private final Long id;

    /**
     * 정책명
     */
    private final String name;

    /**
     * 최소 주문 금액
     * (이 금액을 넘어야 정책이 적용(
     */
    private final int minPrice;

    /**
     * 배송비
     */
    private final int fee;
}
