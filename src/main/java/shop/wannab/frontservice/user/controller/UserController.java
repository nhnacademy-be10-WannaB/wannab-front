package shop.wannab.frontservice.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import shop.wannab.frontservice.user.dto.UserResponse;
import shop.wannab.frontservice.auth.service.AuthService;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final AuthService authService;

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
