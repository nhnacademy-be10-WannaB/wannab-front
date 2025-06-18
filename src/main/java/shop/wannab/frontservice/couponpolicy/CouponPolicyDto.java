package shop.wannab.frontservice.couponpolicy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponPolicyDto {
    private Long id;
    private String name;
    private String discountType;
    private String couponType;
    private String purchaseTerm;
    private String discount;
    private String period;
    private boolean autoIssue;
}
