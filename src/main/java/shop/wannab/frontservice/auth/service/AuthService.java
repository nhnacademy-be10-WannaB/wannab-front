package shop.wannab.frontservice.auth.service;

import io.jsonwebtoken.JwtException;
import shop.wannab.frontservice.auth.controller.request.LoginRequest;
import shop.wannab.frontservice.auth.controller.response.LoginResponse;

public interface AuthService {
    public LoginResponse login(LoginRequest request);

    public String validAccessToken(String accessToken, String refreshToken) throws JwtException;
}
