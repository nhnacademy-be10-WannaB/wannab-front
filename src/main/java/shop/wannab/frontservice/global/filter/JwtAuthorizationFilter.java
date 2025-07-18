package shop.wannab.frontservice.global.filter;


import static shop.wannab.frontservice.utils.CookieUtils.createCookie;
import static shop.wannab.frontservice.utils.CookieUtils.getCookieValue;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.wannab.frontservice.auth.domain.CustomUserDetails;
import shop.wannab.frontservice.auth.domain.TokenPayloadRequest;
import shop.wannab.frontservice.auth.domain.TokenPayloadResponse;
import shop.wannab.frontservice.auth.service.AuthService;
import shop.wannab.frontservice.utils.AuthUtils;

@Slf4j
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
        String newAccessToken = null;
        try {
            newAccessToken = authService.validAccessToken(accessToken, refreshToken);
            if(!Objects.equals(accessToken, newAccessToken)){
                response.addCookie(createCookie(
                        "access_token",
                        newAccessToken,
                        60 * 60,
                        true
                ));
            }
            request.setAttribute("access_token", newAccessToken);
        } catch (JwtException e) {
            response.sendRedirect("/auth/login-form");
            return;
        }

        TokenPayloadResponse payloadResponse = authService.getPayload(new TokenPayloadRequest(newAccessToken));
        Claims claims = Jwts.claims(payloadResponse.claims());
        CustomUserDetails userDetails = new CustomUserDetails(
                List.of(
                    new SimpleGrantedAuthority(
                        AuthUtils.addRolePrefix(
                            claims.get("role", String.class)
                ))));
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        filterChain.doFilter(request, response);
    }
}
