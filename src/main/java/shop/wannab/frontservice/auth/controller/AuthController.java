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

    @PostMapping("/users")
    public String createUser(@ModelAttribute @Valid UserCreateForm userCreateDTO,
                             Model model) {
        String errMessage = authService.createUser(userCreateDTO);
        if (!errMessage.equals("success")) {
            model.addAttribute("errMessage", errMessage);
            return "redirect:/auth/login";
        }
        return "redirect:/";
    }

}
