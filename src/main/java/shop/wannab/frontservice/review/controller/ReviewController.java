package shop.wannab.frontservice.review.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.wannab.frontservice.review.client.response.UserReviewListResponse;
import shop.wannab.frontservice.review.controller.request.CreateReviewRequest;
import shop.wannab.frontservice.review.controller.request.UpdateReviewRequest;
import shop.wannab.frontservice.review.service.ReviewService;
import shop.wannab.frontservice.user.dto.UserPageResponse;
import shop.wannab.frontservice.user.model.UserViewModel;
import shop.wannab.frontservice.user.service.UserService;

@Controller
@RequestMapping("/user/mypage-review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    @PostMapping("/register")
    public String createReview(@ModelAttribute CreateReviewRequest request){
        reviewService.createReview(request);
        return "redirect:/user/mypage-review";
    }

    @GetMapping
    public String getMyReviews(HttpServletRequest request, Model model){
        model.addAttribute("currentUri", request.getRequestURI());

        UserPageResponse user = userService.readUser();

        UserViewModel viewModel = UserViewModel.builder()
                .id(user.username())
                .password(user.password())
                .phone(user.phone())
                .birth(user.birth())
                .nickname(user.nickname())
                .email(user.email())
                .name(user.name())
                .points(user.points())
                .grade(user.grade())
                .build();

        model.addAttribute("user", viewModel);

        UserReviewListResponse response = reviewService.getMyReviews();

        model.addAttribute("reviews",response.content());

        return "user/mypage-review";
    }

    @PutMapping("/update")
    public String updateReview(@ModelAttribute UpdateReviewRequest request){
        reviewService.updateReview(request);

        return "redirect:/user/mypage-review";
    }
}
