package shop.wannab.frontservice.user.service;

import org.springframework.http.ResponseEntity;
import shop.wannab.frontservice.user.dto.LoginRequest;
import shop.wannab.frontservice.user.dto.LoginResponse;

public interface AuthService {
    public LoginResponse login(LoginRequest request);
}
