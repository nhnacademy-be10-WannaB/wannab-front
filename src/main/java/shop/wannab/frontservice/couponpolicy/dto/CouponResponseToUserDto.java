package shop.wannab.frontservice.couponpolicy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CouponResponseToUserDto {
    private String couponName;
    private String discountInfo;
    private String purchaseTerm;
    private String period;
    private String usageStatus;
}
