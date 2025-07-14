package shop.wannab.frontservice.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import shop.wannab.frontservice.user.dto.UserPageResponse;
import shop.wannab.frontservice.user.dto.UserUpdateRequest;
import shop.wannab.frontservice.user.model.UserViewModel;
import shop.wannab.frontservice.user.service.UserService;
import shop.wannab.frontservice.utils.CookieUtils;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/auth/users")
    public String users(){
        return "auth/signup";
    }

    @PatchMapping("/users")
    public String updateUser(@ModelAttribute @Valid UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userUpdateRequest);
        return "redirect:/user/mypage";
    }

    @DeleteMapping("/users")
    public String deleteUser(HttpServletResponse response) {
        userService.deleteUser();
        CookieUtils.deleteAuthCookies(response);
        return "redirect:/auth/login-form";
    }

    @GetMapping("/user/mypage")
    public String mypageEdit(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        UserPageResponse response = userService.readUser();
        UserViewModel viewModel = UserViewModel.builder()
                .points(response.points())
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
}
