package shop.wannab.frontservice.order.client;

import jakarta.servlet.http.Cookie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import shop.wannab.frontservice.order.dto.OrderBookInfoListDto;
import shop.wannab.frontservice.order.dto.OrderItemListDto;
import shop.wannab.frontservice.order.dto.OrderPageRequestDto;

@FeignClient(name = "order-payment-service")
public interface OrderApiClient {

    @PostMapping
    Cookie createCart(@RequestHeader(value = "X-User-Id", required = false) Long userIdentifier);

    @PostMapping("/api/orders")
    OrderPageRequestDto getNecesaryOrderInfo(@RequestHeader("X-User-Id") Long userId, @RequestBody OrderItemListDto orderItemListDto);

    @GetMapping("/api/cart")
    OrderBookInfoListDto getCartItems(@RequestHeader("X-User-Id") Long userId);

    @PostMapping("/api/cart/books")
    OrderBookInfoListDto addProductToCart(@RequestHeader("X-User-Id") Long userId, @RequestParam Long bookId);

    @PutMapping("/api/cart/books/{book-id}")
    OrderBookInfoListDto updateCartItemQuantity(@RequestHeader("X-User-Id") Long userId, @PathVariable(name = "book-id") Long bookId, @RequestParam int quantity);

    @DeleteMapping("/api/cart/books/{book-id}")
    OrderBookInfoListDto removeProductFromCart(@RequestHeader("X-User-Id") Long userId, @PathVariable(name = "book-id") Long bookId);
}
