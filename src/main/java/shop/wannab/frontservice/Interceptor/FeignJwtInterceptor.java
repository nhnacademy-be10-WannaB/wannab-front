package shop.wannab.frontservice.Interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.wannab.frontservice.user.dto.LoginResponse;

@Component
@RequiredArgsConstructor
public class FeignJwtInterceptor implements RequestInterceptor {

    private final HttpServletRequest request;

    @Override
    public void apply(RequestTemplate template) {
//        if (request.getCookies() != null) {
//            for (Cookie cookie : request.getCookies()) {
//                if ("access_token".equals(cookie.getName())) {
//                    String accessToken = cookie.getValue();
//                    template.header("Authorization", "Bearer " + accessToken);
//                } else if ("refresh_token".equals(cookie.getName())) {
//                    String refreshToken = cookie.getValue();
//                    template.header("X-REFRESH-TOKEN", refreshToken);
//                }
//            }
//        }
        template.header("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsInJvbGUiOiJVU0VSIiwiaWF0IjoxNzUwMzE4NjA2LCJleHAiOjE3NTAzMTg2MDZ9.5_dcpVvuic9shpqMNTtmfZtzCc474tvKNnl6b6Xw2G0");
        template.header("X-REFRESH-TOKEN", "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsInJvbGUiOiJVU0VSIiwiaWF0IjoxNzUwMzE4NjA2LCJleHAiOjE3NTA5MjM0MDZ9.FqecMTg-ih8hb3aANN9ZnbRqcMd-yvhOmkD9Nk-fqvM");
    }
}
