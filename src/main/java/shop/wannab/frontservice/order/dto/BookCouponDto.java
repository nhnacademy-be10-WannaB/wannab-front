package shop.wannab.frontservice.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookCouponDto {
    private Long couponId;
    private String couponName;
    private int discountValue;
    private DiscountType discountType;
}
