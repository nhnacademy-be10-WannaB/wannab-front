package shop.wannab.frontservice.user.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.wannab.frontservice.category.controller.response.PageResponse;
import shop.wannab.frontservice.user.dto.PointHistoryResponse;
import shop.wannab.frontservice.user.dto.PointPageResponse;
import shop.wannab.frontservice.user.dto.PointPolicyCreateForm;
import shop.wannab.frontservice.user.dto.PointPolicyUpdateForm;
import shop.wannab.frontservice.user.dto.UserPageResponse;
import shop.wannab.frontservice.user.model.UserViewModel;
import shop.wannab.frontservice.user.service.PointService;
import shop.wannab.frontservice.user.service.UserService;

@Controller
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;
    private final UserService userService;

    @GetMapping("/admin/point")
    public String point(Model model) {
        List<PointPageResponse> pointPolicies =  pointService.readPointPolicy();
        model.addAttribute("pointPolicies", pointPolicies);
        return "admin/point";
    }

    @PatchMapping("/admin/point")
    public String updatePoint(@ModelAttribute @Valid PointPolicyUpdateForm pointPolicyUpdateForm, Model model) {
        pointService.updatePointPolicy(pointPolicyUpdateForm);
        List<PointPageResponse> pointPolicies =  pointService.readPointPolicy();
        model.addAttribute("pointPolicies", pointPolicies);
        return "admin/point";
    }

    @PostMapping("/admin/point")
    public String createPoint(@ModelAttribute @Valid PointPolicyCreateForm pointPolicyCreateForm, Model model) {
        pointService.createPointPolicy(pointPolicyCreateForm);
        List<PointPageResponse> pointPolicies =  pointService.readPointPolicy();
        model.addAttribute("pointPolicies", pointPolicies);
        return "admin/point";
    }

    @GetMapping("/point-histories")
    public String histories(@RequestParam(defaultValue = "0") int page, Model model) {
        PageResponse<PointHistoryResponse> pointHistories = pointService.readPointHistories(page);
        model.addAttribute("pointHistories", pointHistories);
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
        return "/user/my-page-point-histories";
    }
}
