package shop.wannab.frontservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class MypageController {

    @GetMapping("/mypage")
    public String mypageEdit(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        return "user/mypage-edit";
    }

//    @GetMapping("/mypage-address")
//    public String mypageAddress(HttpServletRequest request, Model model) {
//        model.addAttribute("currentUri", request.getRequestURI());
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
