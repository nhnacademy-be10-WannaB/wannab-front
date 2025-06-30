package shop.wannab.frontservice.couponpolicy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssuableCouponDto {
    private Long couponPolicyId;
    private String name;
    private String discountInfo;
}
