package shop.wannab.frontservice.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import shop.wannab.frontservice.auth.controller.request.TokenRequest;
import shop.wannab.frontservice.auth.controller.response.LoginResponse;
import shop.wannab.frontservice.auth.domain.PrincipalDetails;
import shop.wannab.frontservice.auth.service.AuthService;
import shop.wannab.frontservice.utils.CookieUtils;


@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        boolean isSignuped = ((PrincipalDetails) authentication.getPrincipal()).isSignedIn();
        if(isSignuped) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            TokenRequest tokenRequest = new TokenRequest(principalDetails.getUserId(), principalDetails.getRole());

            LoginResponse token = authService.generateToken(tokenRequest);

            response.addCookie(CookieUtils.createCookie("access_token", token.accessToken(), 1800, true));
            response.addCookie(CookieUtils.createCookie("refresh_token", token.refreshToken(), 7 * 24 * 60, true));
            response.sendRedirect("/");

        }else{
            log.warn("onAuthenticationSuccess redirect");
            response.sendRedirect("/auth/login-form");
        }

    }

}
