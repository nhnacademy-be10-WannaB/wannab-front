package shop.wannab.frontservice.couponpolicy;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "coupon-service",url = "http://localhost:8082")
public interface CouponApiClient {
    @GetMapping("/api/admin/coupon_policies")
    List<CouponPolicyDto> getCouponPolicies();

    @PostMapping("/api/admin/coupon_policies")
    void createCouponPolicy(@RequestBody CouponPolicyCreateDto couponPolicyCreateDto);

    @DeleteMapping("/api/admin/coupon_policies/{policyId}")
    void deleteCouponPolicy(@PathVariable Long policyId);
}
