package shop.wannab.frontservice.couponpolicy.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import shop.wannab.frontservice.category.controller.response.CategoryHierarchyDto;
import shop.wannab.frontservice.couponpolicy.dto.CouponPageDataDto;
import shop.wannab.frontservice.couponpolicy.dto.CouponPolicyCreateDto;
import shop.wannab.frontservice.couponpolicy.dto.CouponResponseToUserDto;
import shop.wannab.frontservice.couponpolicy.dto.IssuableCouponDto;
import shop.wannab.frontservice.couponpolicy.dto.PageResponseDto;

@FeignClient(name = "gateway", url = "${gateway.api.url}", path = "/coupon-service", contextId = "couponApiClient")
public interface CouponApiClient {

    @GetMapping("/api/admin/coupon_policies")
    CouponPageDataDto getCouponPoliciesPageData();

    @PostMapping("/api/admin/coupon_policies")
    void createCouponPolicy(@RequestBody CouponPolicyCreateDto couponPolicyCreateDto);

    @DeleteMapping("/api/admin/coupon_policies/{policyId}")
    void deleteCouponPolicy(@PathVariable Long policyId);

    @GetMapping("/api/categories/hierarchy")
    List<CategoryHierarchyDto> getCategoryHierarchy();

    @GetMapping("/api/coupons/issuable-coupons")
    List<IssuableCouponDto> getIssuableCoupons(
            @RequestParam("bookId") Long bookId);

    @PostMapping("/api/coupons/issue/custom")
    void issueCustomCoupon(@RequestParam Long couponPolicyId);

    @GetMapping("/api/coupons/me")
    PageResponseDto<CouponResponseToUserDto> getCoupons(
            @RequestParam("page") int page,
            @RequestParam("size") int size);
}
