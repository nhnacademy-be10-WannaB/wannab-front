package shop.wannab.frontservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.wannab.frontservice.couponpolicy.client.CouponApiClient;
import shop.wannab.frontservice.couponpolicy.dto.CouponResponseToUserDto;
import shop.wannab.frontservice.couponpolicy.dto.PageResponseDto;
import shop.wannab.frontservice.user.dto.UserPageResponse;
import shop.wannab.frontservice.user.model.UserViewModel;
import shop.wannab.frontservice.user.service.UserService;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class MypageController {
    private final UserService userService;
    private final CouponApiClient couponApiClient;


//    @GetMapping("/mypage-order")
//    public String mypageOrder(HttpServletRequest request, Model model) {
//        model.addAttribute("currentUri", request.getRequestURI());
//        return "user/mypage-order";
//    }

//    @GetMapping("/mypage-liked")
//    public String mypageLiked(HttpServletRequest request, Model model) {
//        model.addAttribute("currentUri", request.getRequestURI());
//        return "user/mypage-liked";
//    }

//    @GetMapping("/mypage-review")
//    public String mypageReview(HttpServletRequest request, Model model) {
//        model.addAttribute("currentUri", request.getRequestURI());
//        return "user/mypage-review";
//    }

    @GetMapping("/mypage-coupon")
    public String mypageCoupon(
            @PageableDefault(size = 10) Pageable pageable,
            HttpServletRequest request,
            Model model) {

        PageResponseDto<CouponResponseToUserDto> couponPage = couponApiClient.getCoupons(
                pageable.getPageNumber(),
                pageable.getPageSize()
        );

        UserPageResponse response = userService.readUser();
        UserViewModel viewModel = UserViewModel.builder()
                .id(response.username())
                .password(response.password())
                .phone(response.phone())
                .birth(response.birth())
                .nickname(response.nickname())
                .email(response.email())
                .name(response.name())
                .build();
        model.addAttribute("user", viewModel);

        int nowPage = couponPage.getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, couponPage.getTotalPages());

        model.addAttribute("coupons", couponPage);
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "user/mypage-coupon";
    }
}
