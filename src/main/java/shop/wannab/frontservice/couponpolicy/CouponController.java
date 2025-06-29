package shop.wannab.frontservice.couponpolicy;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class CouponController {
    private final CouponApiClient couponApiClient;

    public CouponController(CouponApiClient couponApiClient) {
        this.couponApiClient = couponApiClient;
    }


    @GetMapping("/coupon")
    public String couponPage(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        List<CategoryHierarchyDto> categoryHierarchy = couponApiClient.getCategoryHierarchy();
        List<CouponPolicyDto> couponPolicies = couponApiClient.getCouponPolicies();
        CouponPolicyCreateDto couponPolicyCreateDto = new CouponPolicyCreateDto();

        model.addAttribute("categoryHierarchy", categoryHierarchy);
        model.addAttribute("couponPolicyCreateDto",couponPolicyCreateDto);
        model.addAttribute("couponPolicies", couponPolicies);
        return "admin/coupon";
    }

    @PostMapping("/coupon")
    public String createCoupon(
            @ModelAttribute CouponPolicyCreateDto requestDto,
            RedirectAttributes redirectAttributes) {
        try {
            couponApiClient.createCouponPolicy(requestDto);
            redirectAttributes.addFlashAttribute("successMessage", "쿠폰 정책이 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "쿠폰 정책 등록에 실패했습니다: " + e.getMessage());
        }
        return "redirect:/admin/coupon";
    }

    @DeleteMapping("/coupons/{couponPolicyId}")
    public String deleteCouponPolicy(@PathVariable Long couponPolicyId
            , RedirectAttributes redirectAttributes) {
        try{
            couponApiClient.deleteCouponPolicy(couponPolicyId);
            redirectAttributes.addFlashAttribute("successMessage","쿠폰 정책이 성공적으로 삭제되었습니다.");
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage","쿠폰 정책 등록에 실패했습니다.: " + e.getMessage());
        }
        return "redirect:/admin/coupon";
    }
}
