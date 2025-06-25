package shop.wannab.frontservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.wannab.frontservice.user.dto.UserPageResponse;
import shop.wannab.frontservice.user.model.UserViewModel;
import shop.wannab.frontservice.user.service.UserService;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class MypageController {
    private final UserService userService;

    @GetMapping("/mypage")
    public String mypageEdit(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
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
        return "user/mypage-edit";
    }

//    @GetMapping("/mypage-address")
//    public String mypageAddress(HttpServletRequest request, Model model) {
//        model.addAttribute("currentUri", request.getRequestURI());
//
//        return "user/mypage-address";
//    }

    @GetMapping("/mypage-order")
    public String mypageOrder(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        return "user/mypage-order";
    }

    @GetMapping("/mypage-liked")
    public String mypageLiked(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        return "user/mypage-liked";
    }

    @GetMapping("/mypage-review")
    public String mypageReview(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        return "user/mypage-review";
    }
}
