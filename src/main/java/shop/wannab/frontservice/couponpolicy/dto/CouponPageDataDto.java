package shop.wannab.frontservice.couponpolicy.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import shop.wannab.frontservice.category.controller.response.CategoryHierarchyDto;

@Getter
@Setter
public class CouponPageDataDto {
    private List<CategoryHierarchyDto> categoryHierarchy;
    private List<CouponPolicyDto> couponPolicies;

}
