package shop.wannab.frontservice.user.service.Impl;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.user.AuthClient;
import shop.wannab.frontservice.user.dto.LoginRequest;
import shop.wannab.frontservice.user.dto.LoginResponse;
import shop.wannab.frontservice.user.service.AuthService;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthClient authClient;

    @Override
    public LoginResponse login(LoginRequest request) {
        ResponseEntity response = authClient.login(request);
        LoginResponse tokenDto = (LoginResponse) response.getBody();

        return tokenDto;
    }
}
