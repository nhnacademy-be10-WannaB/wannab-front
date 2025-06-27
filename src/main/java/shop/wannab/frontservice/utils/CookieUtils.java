package shop.wannab.frontservice.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

public class CookieUtils {

    private CookieUtils(){}

    public static Cookie createCookie(String cookieName, String value, int age, boolean httpOnly) {
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setHttpOnly(httpOnly);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(age);
        return cookie;
    }

    public static String getCookieValue(HttpServletRequest request, String name) {
        if (request.getCookies() == null) return null;
        Optional<Cookie> cookie = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(name))
                .findFirst();
        return cookie.map(Cookie::getValue).orElse(null);
    }

}
