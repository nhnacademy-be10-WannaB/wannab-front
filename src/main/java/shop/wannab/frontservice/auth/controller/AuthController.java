package shop.wannab.frontservice.auth.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.wannab.frontservice.auth.controller.request.LoginRequest;
import shop.wannab.frontservice.auth.controller.response.LoginResponse;
import shop.wannab.frontservice.auth.service.AuthService;
import shop.wannab.frontservice.user.dto.UserCreateForm;
import shop.wannab.frontservice.utils.CookieUtils;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public void login(@ModelAttribute LoginRequest request, HttpServletResponse response) throws IOException {
        LoginResponse loginResponse = authService.login(request);
        response.addCookie(CookieUtils.createCookie("access_token", loginResponse.accessToken(), 1800, true));
        response.addCookie(CookieUtils.createCookie("refresh_token", loginResponse.refreshToken(), 7 * 24 * 60, true));
        response.sendRedirect("/user/main");
    }

    @PostMapping("/users")
    public String createUser(@ModelAttribute @Valid UserCreateForm userCreateDTO,
                             Model model) {
        String errMessage = authService.createUser(userCreateDTO);
        if (!errMessage.equals("success")) {
            model.addAttribute("errMessage", errMessage);
            return "/auth/login";
        }
        return "/user/main";
    }

}
