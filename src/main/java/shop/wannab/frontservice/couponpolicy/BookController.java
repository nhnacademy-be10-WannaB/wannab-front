package shop.wannab.frontservice.couponpolicy;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class BookController {
    private final CouponApiClient couponApiClient;

    public BookController(CouponApiClient couponApiClient) {
        this.couponApiClient = couponApiClient;
    }
    @GetMapping("/main-book-detail/{bookId}")
    public String mainBookDetail(
            @PathVariable Long bookId,
            Model model) {
        List<IssuableCouponDto> couponList = couponApiClient.getIssuableCoupons(bookId);
        model.addAttribute("coupons", couponList);
        return "user/main-book-detail";
    }

    @PostMapping("/main-book-detail")
    public String mainBookDetail(@RequestParam Long couponPolicyId) {
        couponApiClient.issueCustomCoupon(couponPolicyId);
        return "redirect:/user/main";
    }
}
