package shop.wannab.frontservice.global.filter;


import static shop.wannab.frontservice.utils.CookieUtils.createCookie;
import static shop.wannab.frontservice.utils.CookieUtils.getCookieValue;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.wannab.frontservice.auth.service.AuthService;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final AuthService authService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String accessToken = getCookieValue(request, "access_token");
        String refreshToken = getCookieValue(request, "refresh_token");

        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String newAccessToken = authService.validAccessToken(accessToken, refreshToken);
            if(!Objects.equals(accessToken, newAccessToken)){
                response.addCookie(createCookie(
                        "access_token",
                        newAccessToken,
                        15 * 60,
                        true
                ));
            }
            request.setAttribute("access_token", newAccessToken);
        } catch (JwtException e) {
            response.sendRedirect("/auth/login");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
