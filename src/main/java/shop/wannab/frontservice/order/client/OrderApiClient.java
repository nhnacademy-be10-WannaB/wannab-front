package shop.wannab.frontservice.order.client;

import jakarta.servlet.http.Cookie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import shop.wannab.frontservice.order.dto.*;

@FeignClient(name = "order-payment-service")
public interface OrderApiClient {

    @PostMapping
    Cookie createCart(@RequestHeader(value = "X-USER-ID", required = false) Long userIdentifier);

    @PostMapping("/api/orders")
    OrderPageRequestDto getNecesaryOrderInfo(@RequestHeader("X-USER-ID") Long userId, @RequestBody OrderItemListDto orderItemListDto);

    @GetMapping("/api/cart")
    OrderBookInfoListDto getCartItems(@RequestHeader("X-USER-ID") Long userId);

    @PostMapping("/api/cart/books")
    OrderBookInfoListDto addProductToCart(@RequestHeader("X-USER-ID") Long userId, @RequestParam Long bookId);

    @PutMapping("/api/cart/books/{book-id}")
    OrderBookInfoListDto updateCartItemQuantity(@RequestHeader("X-USER-ID") Long userId, @PathVariable(name = "book-id") Long bookId, @RequestParam int quantity);

    @DeleteMapping("/api/cart/books/{book-id}")
    OrderBookInfoListDto removeProductFromCart(@RequestHeader("X-USER-ID") Long userId, @PathVariable(name = "book-id") Long bookId);

    @PostMapping("/api/orders/new")
    OrderInfoForPayment processOrder(@RequestHeader("X-USER-ID") Long userId, @RequestBody OrderSubmitDto orderSubmitDto);

}
