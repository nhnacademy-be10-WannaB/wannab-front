package shop.wannab.frontservice.auth.service;

import io.jsonwebtoken.JwtException;
import org.springframework.web.bind.annotation.RequestParam;
import shop.wannab.frontservice.auth.controller.request.LoginRequest;
import shop.wannab.frontservice.auth.controller.request.TokenRequest;
import shop.wannab.frontservice.auth.controller.request.UnlockRequest;
import shop.wannab.frontservice.auth.controller.response.LoginResponse;
import shop.wannab.frontservice.auth.domain.TokenResponse;
import shop.wannab.frontservice.user.dto.UserCreateForm;

public interface AuthService {
    LoginResponse login(LoginRequest request);

    String validAccessToken(String accessToken, String refreshToken) throws JwtException;

    /**
     * 회원 등록 Create
     */
    String createUser(UserCreateForm dto);

    /**
     * 토큰 발급
     */
    LoginResponse generateToken(TokenRequest tokenRequest);

    /**
     * 휴면해제 인증코드 체크
     */
    boolean verifyDormantAccount(UnlockRequest unlockRequest);

    /**
     * 휴면해제 인증코드 요청
     */
    void resendDormantAuthCode(String userId);

    /**
     * 아이디 중복 체크
     */
    boolean duplicatedId(String id);
}
