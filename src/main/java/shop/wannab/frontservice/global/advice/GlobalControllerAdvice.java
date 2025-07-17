package shop.wannab.frontservice.global.advice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import shop.wannab.frontservice.auth.service.AuthService;


@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
    private final AuthService authService;

    @ModelAttribute("isLoggedIn")
    public boolean isLoggedIn() {
        return authService.isLogined();
    }
}
