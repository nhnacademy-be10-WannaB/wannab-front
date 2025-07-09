package shop.wannab.frontservice.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import shop.wannab.frontservice.auth.exception.DeletedUserException;
import shop.wannab.frontservice.auth.exception.InactiveUserException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        Throwable throwable = exception.getCause();
        if (throwable instanceof InactiveUserException inactive) {

            response.sendRedirect("/auth/unlock?userId=" + inactive.getMessage());
            return;
        } else {
            response.sendRedirect("/auth/login");
        }
    }
}
