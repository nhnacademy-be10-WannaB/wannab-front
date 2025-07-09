package shop.wannab.frontservice.couponpolicy;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.wannab.frontservice.book.client.AdminBookClient;
import shop.wannab.frontservice.book.client.BookClient;

@Controller
@RequestMapping("/admin/coupons")
@RequiredArgsConstructor
public class CouponController {
    private final CouponApiClient couponApiClient;
    private final AdminBookClient adminBookClient;

    @GetMapping
    public String couponPage(@RequestParam(value = "query", required = false) String query,
                             @PageableDefault(size = 10) Pageable pageable
            , HttpServletRequest request, Model model) {
        if (query != null && !query.isEmpty()) {
            Page<BookCouponInfoDto> bookPage = adminBookClient.getBookCouponInfoList(
                    query,
                    pageable.getPageNumber(),
                    pageable.getPageSize()
            );
            model.addAttribute("bookPage", bookPage);
            model.addAttribute("query", query);
        } else {
            CouponPageDataDto couponPageDataDto = couponApiClient.getCouponPoliciesPageData();
            model.addAttribute("categoryHierarchy", couponPageDataDto.getCategoryHierarchy());
            model.addAttribute("couponPolicies", couponPageDataDto.getCouponPolicies());
        }
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("couponPolicyCreateDto", new CouponPolicyCreateDto());
        return "admin/coupon";
    }

    @PostMapping
    public String createCoupon(
            @ModelAttribute CouponPolicyCreateDto requestDto,
            RedirectAttributes redirectAttributes) {
        try {
            couponApiClient.createCouponPolicy(requestDto);
            redirectAttributes.addFlashAttribute("successMessage", "쿠폰 정책이 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "쿠폰 정책 등록에 실패했습니다: " + e.getMessage());
        }
        return "redirect:/admin/coupons";
    }

    @DeleteMapping("/{couponPolicyId}")
    public String deleteCouponPolicy(@PathVariable Long couponPolicyId
            , RedirectAttributes redirectAttributes) {
        try {
            couponApiClient.deleteCouponPolicy(couponPolicyId);
            redirectAttributes.addFlashAttribute("successMessage", "쿠폰 정책이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "쿠폰 정책 등록에 실패했습니다.: " + e.getMessage());
        }
        return "redirect:/admin/coupons";
    }
}
