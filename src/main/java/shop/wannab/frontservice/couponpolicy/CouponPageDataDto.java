package shop.wannab.frontservice.couponpolicy;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponPageDataDto {
    private List<CategoryHierarchyDto> categoryHierarchy;
    private List<CouponPolicyDto> couponPolicies;

}
