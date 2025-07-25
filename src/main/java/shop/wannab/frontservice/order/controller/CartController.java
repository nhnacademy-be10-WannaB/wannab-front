package shop.wannab.frontservice.order.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.wannab.frontservice.auth.service.AuthService;
import shop.wannab.frontservice.order.client.OrderApiClient;
import shop.wannab.frontservice.order.dto.GuestCartCookieDto;
import shop.wannab.frontservice.order.dto.OrderBookInfoListDto;
import shop.wannab.frontservice.order.service.CartOrderService;

@Slf4j
@Controller
@RequestMapping("/main-cart")
@RequiredArgsConstructor
public class CartController {
    private final OrderApiClient orderApiClient;
    private final CartOrderService cartOrderService;
    private final AuthService authService;

    @GetMapping
    public String getCartPage(@CookieValue(value = "guestId", required = false) Long guestId, Model model) {
        if (Objects.isNull(guestId) && !authService.isLogined()) {//비회원 && 장바구니에 아무것도 담지 않을시
            OrderBookInfoListDto emptyCart = new OrderBookInfoListDto(List.of());
            model.addAttribute("cartItems", emptyCart.getOrderBookInfos());
            return "public/main-cart";
        }
        OrderBookInfoListDto cartItems = orderApiClient.getCartItems(guestId);
        model.addAttribute("cartItems", cartItems.getOrderBookInfos());
        return "public/main-cart";
    }

    @PostMapping("/books")
    public String addItemToCart(@CookieValue(value = "guestId", required = false) Long guestId,
                                @RequestParam Long bookId, HttpServletResponse response) {
        if (Objects.isNull(guestId) && !authService.isLogined()) {//비회원 && 장바구니에 처음 상품 담을시
            GuestCartCookieDto guestCartCookieDto = orderApiClient.createCart();
            cartOrderService.setGuestCookie(guestCartCookieDto, response);
            guestId = guestCartCookieDto.getValue();
        }
        orderApiClient.addProductToCart(guestId, bookId);
        return "redirect:/main-cart";
    }

    @PutMapping("/books/{book-id}")
    public String updateCartItemQuantity(@CookieValue(value = "guestId", required = false) Long guestId,
                                         @PathVariable(name = "book-id") Long bookId,
                                         @RequestParam int quantity) {
        if (Objects.nonNull(guestId) || authService.isLogined()) {
            orderApiClient.updateCartItemQuantity(guestId, bookId, quantity);
        }
        return "redirect:/main-cart";
    }

    @DeleteMapping("/books/{book-id}")
    public String removeCartItem(@CookieValue(value = "guestId", required = false) Long guestId,
                                 @PathVariable(name = "book-id") Long bookId) {
        if (Objects.nonNull(guestId) || authService.isLogined()) {
            orderApiClient.removeProductFromCart(guestId, bookId);
        }
        return "redirect:/main-cart";
    }
}
