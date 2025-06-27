package shop.wannab.frontservice.couponpolicy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponResponseToUserDto {
    private String couponName;
    private String discountInfo;
    private String purchaseTerm;
    private String period;
    private String usageStatus;
}
