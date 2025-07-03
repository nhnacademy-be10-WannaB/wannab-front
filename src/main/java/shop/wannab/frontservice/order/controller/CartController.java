package shop.wannab.frontservice.order.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.wannab.frontservice.order.client.OrderApiClient;
import shop.wannab.frontservice.order.dto.GuestCartCookieDto;
import shop.wannab.frontservice.order.dto.OrderBookInfoListDto;
import shop.wannab.frontservice.order.service.CartService;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/user/main-cart")
@RequiredArgsConstructor
public class CartController {
    private final OrderApiClient orderApiClient;
    private final CartService cartService;
    @GetMapping
    public String getCartPage(@CookieValue(value = "guestId", required = false) Long guestId, Model model) {
        if (Objects.isNull(guestId)) {//비회원 && 장바구니에 아무것도 담지 않을시
            OrderBookInfoListDto emptyCart = new OrderBookInfoListDto(List.of());
            model.addAttribute("cartItems", emptyCart.getOrderBookInfos());
            return "user/main-cart";
        }
        OrderBookInfoListDto cartItems = orderApiClient.getCartItems(guestId);
        model.addAttribute("cartItems", cartItems.getOrderBookInfos());
        return "user/main-cart";
    }

    @PostMapping("/books")
    public String addItemToCart(@CookieValue(value = "guestId", required = false) Long guestId, @RequestParam Long bookId, HttpServletResponse response) {
        if (Objects.isNull(guestId)) {//비회원 && 장바구니에 처음 상품 담을시 //TODO: 로그아웃시,jwt토큰 쿠키 아예 지우는지 냅두는지 확인할 필요 ㅇ
            GuestCartCookieDto guestCartCookieDto = orderApiClient.createCart();
            cartService.setGuestCookie(guestCartCookieDto, response);
            guestId = guestCartCookieDto.getValue();
        }
        orderApiClient.addProductToCart(guestId, bookId);
        return "redirect:/user/main-cart";
    }

    @PutMapping("/books/{book-id}")
    public String updateCartItemQuantity(@CookieValue(value = "guestId", required = false) Long guestId, @PathVariable(name = "book-id") Long bookId, @RequestParam int quantity) {
        if (Objects.nonNull(guestId)) {
            orderApiClient.updateCartItemQuantity(guestId, bookId, quantity);
        }
        return "redirect:/user/main-cart";
    }

    @DeleteMapping("/books/{book-id}")
    public String removeCartItem(@CookieValue(value = "guestId", required = false) Long guestId, @PathVariable(name = "book-id") Long bookId) {
        if (Objects.nonNull(guestId)) {
            orderApiClient.removeProductFromCart(guestId, bookId);
        }
        return "redirect:/user/main-cart";
    }
}
