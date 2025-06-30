package shop.wannab.frontservice.auth.service;

import io.jsonwebtoken.JwtException;
import shop.wannab.frontservice.auth.controller.request.LoginRequest;
import shop.wannab.frontservice.auth.controller.response.LoginResponse;
import shop.wannab.frontservice.user.dto.UserCreateForm;

public interface AuthService {
    LoginResponse login(LoginRequest request);

    String validAccessToken(String accessToken, String refreshToken) throws JwtException;

    /**
     * 회원 등록 Create
     */
    String createUser(UserCreateForm dto);
}
