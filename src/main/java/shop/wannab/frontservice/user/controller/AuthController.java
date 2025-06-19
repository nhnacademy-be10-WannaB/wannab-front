package shop.wannab.frontservice.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import shop.wannab.frontservice.Utils.CookieUtils;
import shop.wannab.frontservice.user.dto.LoginRequest;
import shop.wannab.frontservice.user.dto.LoginResponse;
import shop.wannab.frontservice.user.service.AuthService;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;

    @GetMapping("/asd")
    public ResponseEntity<Void> login() {
        LoginRequest loginRequest = new LoginRequest("dang", "aaaa1111@");
        LoginResponse response = authService.login(loginRequest);
        ResponseCookie accessCookie = CookieUtils.createCookie("access_token", response.accessToken(), Duration.ofMinutes(30), false);
        ResponseCookie refreshCookie = CookieUtils.createCookie("refresh_token", response.refreshToken(), Duration.ofDays(7), true);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .build();
    }
}
