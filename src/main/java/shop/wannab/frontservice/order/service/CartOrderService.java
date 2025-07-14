package shop.wannab.frontservice.order.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.order.dto.GuestCartCookieDto;

@Service
public class CartOrderService {
    public void setGuestCookie(GuestCartCookieDto guestCartCookieDto, HttpServletResponse response) {
        Cookie cookie = new Cookie(guestCartCookieDto.getKeyName(), String.valueOf(guestCartCookieDto.getValue()));
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(guestCartCookieDto.getCookieMaxAge());

        response.addCookie(cookie);
    }
}
