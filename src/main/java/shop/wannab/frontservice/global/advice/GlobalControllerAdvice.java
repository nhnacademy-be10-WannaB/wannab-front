package shop.wannab.frontservice.global.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.wannab.frontservice.auth.CustomUserDetails;


@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler
    public String handleInactiveUserException(Exception e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/auth/login-form";
    }

    @ModelAttribute("isLoggedIn")
    public boolean isLoggedIn(HttpServletRequest request) {
        return request.getAttribute("access_token") != null;
    }
}
