package shop.wannab.frontservice.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shop.wannab.frontservice.Utils.CookieUtils;
import shop.wannab.frontservice.user.dto.LoginRequest;
import shop.wannab.frontservice.user.dto.LoginResponse;
import shop.wannab.frontservice.user.dto.UserResponse;
import shop.wannab.frontservice.user.service.AuthService;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public void login(@ModelAttribute LoginRequest request, HttpServletResponse response) throws IOException {
        LoginResponse loginResponse = authService.login(request);
        ResponseCookie accessCookie = CookieUtils.createCookie("access_token", loginResponse.accessToken(), Duration.ofMinutes(30), false);
        ResponseCookie refreshCookie = CookieUtils.createCookie("refresh_token", loginResponse.refreshToken(), Duration.ofDays(7), true);

        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        response.sendRedirect("/user/main");
    }

    @GetMapping("/user/main")
    public String userMainPage() {
        return "user/main";
    }


    @GetMapping("/test")
    public ResponseEntity<String> test() {
        UserResponse userResponse = authService.test();
        return ResponseEntity.ok(userResponse.toString());
    }
}
