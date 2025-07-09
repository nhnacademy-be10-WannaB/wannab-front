package shop.wannab.frontservice.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import shop.wannab.frontservice.auth.CustomUserDetails;
import shop.wannab.frontservice.auth.controller.request.TokenRequest;
import shop.wannab.frontservice.auth.controller.response.LoginResponse;
import shop.wannab.frontservice.auth.exception.InactiveUserException;
import shop.wannab.frontservice.auth.service.AuthClient;
import shop.wannab.frontservice.utils.CookieUtils;

@Slf4j
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final AuthClient authClient;

    public CustomLoginFilter(AuthenticationManager authenticationManager, AuthClient authClient) {
        this.authenticationManager = authenticationManager;
        this.authClient = authClient;
        setFilterProcessesUrl("/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                username,
                password);
        log.info("로그인 시도 : username : {}, password : {}", username, password);
        return authenticationManager.authenticate(token);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        CustomUserDetails principal = (CustomUserDetails) authResult.getPrincipal();
        String role = principal.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElseThrow(() -> new RuntimeException("권한 없음"));

        LoginResponse token = authClient.getToken(TokenRequest.builder().userId(principal.getId()).role(role).build());
        response.addCookie(CookieUtils.createCookie("access_token", token.accessToken(), 60 * 60, true));
        response.addCookie(CookieUtils.createCookie("refresh_token", token.refreshToken(), 7 * 24 * 60, true));
        response.sendRedirect("/");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        // 예외 직접 처리
        Throwable rootCause = failed.getCause();

        if (rootCause instanceof InactiveUserException inactive) {
            log.info("휴면 계정 로그인 시도");
            response.sendRedirect("/auth/unlock?userId=" + inactive.getMessage());
        } else {
            log.info("로그인 실패");
            response.sendRedirect("/auth/login-form");
        }
    }


}
