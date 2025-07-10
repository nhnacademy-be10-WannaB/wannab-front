package shop.wannab.frontservice.couponpolicy.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssuableCouponDto {
    private Long couponPolicyId;
    private String name;
    private String discountInfo;
}
