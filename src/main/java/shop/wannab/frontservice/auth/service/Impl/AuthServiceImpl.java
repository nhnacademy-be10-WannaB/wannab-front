package shop.wannab.frontservice.auth.service.Impl;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.auth.controller.request.LoginRequest;
import shop.wannab.frontservice.auth.controller.request.ReissueRequest;
import shop.wannab.frontservice.auth.controller.request.UnlockRequest;
import shop.wannab.frontservice.auth.controller.response.LoginResponse;
import shop.wannab.frontservice.auth.controller.response.ReissueResponse;
import shop.wannab.frontservice.auth.controller.request.TokenRequest;
import shop.wannab.frontservice.auth.domain.Response;
import shop.wannab.frontservice.auth.exception.UserAlreadyExistsException;
import shop.wannab.frontservice.auth.exception.UserNotFoundException;
import shop.wannab.frontservice.auth.service.AuthClient;
import shop.wannab.frontservice.auth.service.AuthService;
import shop.wannab.frontservice.user.dto.UserCreateForm;
import shop.wannab.frontservice.user.dto.UserCreateRequest;
import shop.wannab.frontservice.utils.JwtUtils;
import shop.wannab.frontservice.auth.domain.ResponseCode;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthClient authClient;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public LoginResponse login(LoginRequest request) {
        ResponseEntity<LoginResponse> response = authClient.login(request);

        return response.getBody();
    }

    @Override
    public String validAccessToken(String accessToken, String refreshToken) throws JwtException {

        if (accessToken != null && !jwtUtils.isExpired(accessToken)) {
            return accessToken;
        }

        if (refreshToken == null)
            throw new JwtException("AccessToken, RefreshToken 둘 다 유효하지 않음");

        jwtUtils.parse(refreshToken);
        ResponseEntity<ReissueResponse> reissue = authClient.reissue(new ReissueRequest(refreshToken));

        if (reissue.getStatusCode().is2xxSuccessful() && reissue.getBody() != null)
            return reissue.getBody().accessToken();

        throw new JwtException("AccessToken 재발급 중 예외 발생");
    }

    @Override
    public String createUser(UserCreateForm userCreateForm) {
        String encryptedPassword = passwordEncoder.encode(userCreateForm.password());

        UserCreateRequest request = new UserCreateRequest(
                userCreateForm.userId(),
                encryptedPassword,
                userCreateForm.username(),
                userCreateForm.email(),
                userCreateForm.phone(),
                userCreateForm.birthday()
        );

        Response<Void> response = authClient.createUser(request);
        switch (response.getResponseCode()) {
            case ResponseCode.SUCCESS -> { return "success"; }
            case ResponseCode.USER_ALREADY_EXISTS -> { throw new UserAlreadyExistsException();}
            case ResponseCode.USER_NOT_FOUND -> { throw new UserNotFoundException();}
            default -> throw new RuntimeException("예상치 못한 응답입니다: " + response.getResponseCode());
        }
    }

    @Override
    public LoginResponse generateToken(TokenRequest tokenRequest) {
        return authClient.getToken(tokenRequest);
    }

    @Override
    public boolean verifyDormantAccount(UnlockRequest unlockRequest) {
        return authClient.unlockVerifiy(unlockRequest);
    }

    @Override
    public void resendDormantAuthCode(String userId) {
        authClient.unlockRequest(userId);
    }

    @Override
    public boolean duplicatedId(String id) {
        Response response = authClient.duplicatedId(id);
        return (Boolean)response.getData();
    }

    @Override
    public void logout() {
        authClient.logout();
    }

}
