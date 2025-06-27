package shop.wannab.frontservice.couponpolicy;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "coupon-service",url = "${coupon.api.url}")
public interface CouponApiClient {
    @GetMapping("/api/admin/coupon_policies")
    List<CouponPolicyDto> getCouponPolicies();

    @PostMapping("/api/admin/coupon_policies")
    void createCouponPolicy(@RequestBody CouponPolicyCreateDto couponPolicyCreateDto);

    @DeleteMapping("/api/admin/coupon_policies/{policyId}")
    void deleteCouponPolicy(@PathVariable Long policyId);

    @GetMapping("/api/categories/hierarchy")
    List<CategoryHierarchyDto> getCategoryHierarchy();

    @GetMapping("/api/coupons/me")
    PageResponseDto<CouponResponseToUserDto> getCoupons(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam("page") int page,
            @RequestParam("size") int size);
}
