package shop.wannab.frontservice.order.list.deliveryPolicy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPolicyResponse {
    /**
     * 정책 id
     */
    private Long id;

    /**
     * 정책명
     */
    private String name;

    /**
     * 최소 주문 금액
     * (이 금액을 넘어야 정책이 적용(
     */
    private int minPrice;

    /**
     * 배송비
     */
    private int fee;
}
