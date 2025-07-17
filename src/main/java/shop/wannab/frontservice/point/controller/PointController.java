package shop.wannab.frontservice.point.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.wannab.frontservice.category.controller.response.PageResponse;
import shop.wannab.frontservice.point.dto.PointHistoryResponse;
import shop.wannab.frontservice.user.dto.UserPageResponse;
import shop.wannab.frontservice.user.model.UserViewModel;
import shop.wannab.frontservice.point.service.PointService;
import shop.wannab.frontservice.user.service.UserService;

@PreAuthorize("hasRole('USER')")
@Controller
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;
    private final UserService userService;

    @GetMapping("/user/mypage-point-histories")
    public String histories(@RequestParam(defaultValue = "0") int page, Model model, HttpServletRequest request) {
        PageResponse<PointHistoryResponse> pointHistories = pointService.readPointHistories(page);
        model.addAttribute("currentUri", request.getRequestURI());
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
                .points(response.points())
                .build();
        model.addAttribute("user", viewModel);
        return "user/mypage-point-histories";
    }
}
