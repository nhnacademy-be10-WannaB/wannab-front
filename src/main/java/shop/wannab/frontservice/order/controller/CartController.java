package shop.wannab.frontservice.order.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.wannab.frontservice.order.client.OrderApiClient;
import shop.wannab.frontservice.order.dto.OrderBookInfoListDto;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/user/main-cart")
@RequiredArgsConstructor
public class CartController {
    private final OrderApiClient orderApiClient;

    @GetMapping
    public String getCartPage(@CookieValue(value = "X-USER-ID", required = false) Long userId, Model model) {
        // 비회원 && 장바구니에 아무것도 담지 않을시
        if (Objects.isNull(userId)) {
            OrderBookInfoListDto emptyCart = new OrderBookInfoListDto(List.of());
            model.addAttribute("cartItems", emptyCart.getOrderBookInfos());
            return "user/main-cart";
        }
        OrderBookInfoListDto cartItems = orderApiClient.getCartItems(userId);
        model.addAttribute("cartItems", cartItems.getOrderBookInfos());
        return "user/main-cart";
    }

    @PostMapping("/books")
    public String addItemToCart(@CookieValue(value = "X-USER-ID", required = false) Long userId, @RequestParam Long bookId, HttpServletResponse response) {
        // 비회원 && 장바구니에 처음 상품 담을시
        if (Objects.isNull(userId)) {
            Cookie guestIdentifier = orderApiClient.createCart(null);
            response.addCookie(guestIdentifier);
            userId = Long.valueOf(guestIdentifier.getValue());
        }
        orderApiClient.addProductToCart(userId, bookId);
        return "redirect:/user/main-cart";
    }

    @PutMapping("/books/{book-id}")
    public String updateCartItemQuantity(@CookieValue("X-USER-ID") Long userId, @PathVariable(name = "book-id") Long bookId, @RequestParam int quantity) {
        if (Objects.nonNull(userId)) {
            orderApiClient.updateCartItemQuantity(userId, bookId, quantity);
        }
        return "redirect:/user/main-cart";
    }

    @DeleteMapping("/books/{book-id}")
    public String removeCartItem(@CookieValue("X-USER-ID") Long userId, @PathVariable(name = "book-id") Long bookId) {
        if (Objects.nonNull(userId)) {
            orderApiClient.removeProductFromCart(userId, bookId);
        }
        return "redirect:/user/main-cart";
    }
}
