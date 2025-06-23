package shop.wannab.frontservice.filter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.wannab.frontservice.user.AuthClient;

@RequiredArgsConstructor
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final AuthClient authClient; // refresh 토큰으로 재발급하는 feign client
    @Value("${jwt.secret-key}")
    private String secretKey;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("JwtAuthorizationFilter 진입: " + request.getRequestURI());

        // 1. request에서 Cookie - access_token, refresh_token 불러오기
        String accessToken = getCookieValue(request, "access_token");
        String refreshToken = getCookieValue(request, "refresh_token");

        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 2. access_token 검증
            Claims claims = parseJwt(accessToken);
            // 2-1. access_token 만료안됨
            request.setAttribute("access_token", accessToken);
        } catch (ExpiredJwtException e) {
            // 2-2. access_token 만료시
            if (refreshToken == null) {
                response.sendRedirect("/login"); // 재로그인
                return;
            }

            try {
                // refresh_token 검증
                parseJwt(refreshToken);

                // refresh_token으로 새로운 access_token 요청
                String newAccessToken = authClient.reissue(refreshToken).getBody();

                // 새 토큰 쿠키로 설정
                response.addHeader("Set-Cookie",
                        "access_token=" + newAccessToken + "; Path=/; HttpOnly; Max-Age=1800");

                Claims newClaims = parseJwt(newAccessToken);
                request.setAttribute("access_token", newAccessToken);

            } catch (Exception re) {
                response.sendRedirect("/login"); // refresh도 만료됨
            }
        } catch (Exception ex) {
            response.sendRedirect("/login"); // 기타 JWT 예외
        }
        filterChain.doFilter(request, response);
    }

    private Claims parseJwt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String getCookieValue(HttpServletRequest request, String name) {
        if (request.getCookies() == null) return null;
        Optional<Cookie> cookie = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(name))
                .findFirst();
        return cookie.map(Cookie::getValue).orElse(null);
    }
}
