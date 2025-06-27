package shop.wannab.frontservice.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderCouponDto {
    private Long couponId;
    private String couponName;
    private int discountValue;
    private DiscountType discountType;
}
