package shop.wannab.frontservice.global.Interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeignJwtInterceptor implements RequestInterceptor {

    private final HttpServletRequest request;

    @Override
    public void apply(RequestTemplate template) {
        Object tokenObj = request.getAttribute("access_token");
        if (tokenObj instanceof String accessToken) {
            template.header("Authorization", "Bearer " + accessToken);
        }
    }
}
