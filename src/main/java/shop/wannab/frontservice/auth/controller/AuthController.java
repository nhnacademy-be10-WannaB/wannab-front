package shop.wannab.frontservice.auth.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.wannab.frontservice.auth.controller.request.UnlockRequest;
import shop.wannab.frontservice.auth.exception.UserAlreadyExistsException;
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
            return "redirect:/auth/login-form";
        }
        return "redirect:/";
    }

    @GetMapping("/unlock")
    public String unlock(@RequestParam("userId") String userId, Model model) {
        model.addAttribute("userId", userId);
        return "auth/unlock";
    }


    @PostMapping("/unlock/verify")
    public String verifyCode(@RequestParam String userId,
                             @RequestParam int authCode,
                             Model model) {
        boolean result = authService.verifyDormantAccount(new UnlockRequest(userId, authCode));

        if (result) {
            return "redirect:/auth/login-form";
        } else {
            model.addAttribute("userId", userId);
            model.addAttribute("error", "인증코드가 틀렸습니다. 다시 입력해주세요.");
            return "user/unlock";
        }
    }

    @PostMapping("/unlock/request")
    @ResponseBody
    public ResponseEntity resendAuthCode(@RequestParam String userId) {
        authService.resendDormantAuthCode(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/duplicated-id")
    @ResponseBody
    public boolean duplicatedId(@RequestParam String id) {
        return authService.duplicatedId(id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        authService.logout();
        CookieUtils.deleteAuthCookies(response);
        return "redirect:/auth/login-form";
    }

    @ExceptionHandler({UserAlreadyExistsException.class})
    public String handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return "redirect:/auth/login-form";
    }

}
