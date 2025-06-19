package shop.wannab.frontservice.Utils;

import java.time.Duration;
import org.springframework.http.ResponseCookie;

public class CookieUtils {

    public static ResponseCookie createCookie(String cookieName, String value, Duration duration, boolean httpOnly) {
        ResponseCookie.ResponseCookieBuilder builder = ResponseCookie.from(cookieName, value)
                .secure(true)
                .path("/")
                .maxAge(duration);

        if (httpOnly) {
            builder = builder.httpOnly(true);
        }

        return builder.build();
    }

}
