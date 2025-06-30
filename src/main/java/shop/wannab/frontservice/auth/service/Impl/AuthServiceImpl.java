package shop.wannab.frontservice.auth.service.Impl;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.auth.controller.request.LoginRequest;
import shop.wannab.frontservice.auth.controller.request.ReissueRequest;
import shop.wannab.frontservice.auth.controller.response.LoginResponse;
import shop.wannab.frontservice.auth.controller.response.ReissueResponse;
import shop.wannab.frontservice.auth.service.AuthClient;
import shop.wannab.frontservice.auth.service.AuthService;
import shop.wannab.frontservice.user.dto.UserCreateForm;
import shop.wannab.frontservice.user.dto.UserCreateRequest;
import shop.wannab.frontservice.user.dto.UserPageResponse;
import shop.wannab.frontservice.utils.JwtUtils;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthClient authClient;
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
        UserCreateRequest request = new UserCreateRequest(
                userCreateForm.userId(),
                userCreateForm.password(),
                userCreateForm.username(),
                userCreateForm.email(),
                userCreateForm.phone(),
                userCreateForm.birthday()
        );

        ResponseEntity<UserPageResponse> response = authClient.createUser(request);
        switch (response.getStatusCode()) {
            case HttpStatus.CREATED -> { return "success"; }
            case HttpStatus.FORBIDDEN -> { return "허가되지 않은 요청입니다."; }
            case HttpStatus.BAD_REQUEST -> { return "중복된 ID 입니다."; }
            default -> throw new RuntimeException("예상치 못한 응답입니다: " + response.getStatusCode());
        }
    }

}
