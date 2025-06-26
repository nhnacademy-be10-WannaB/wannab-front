package shop.wannab.frontservice.order.client;

import jakarta.servlet.http.Cookie;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import shop.wannab.frontservice.order.list.deliveryPolicy.dto.DeliveryPolicyRequest;
import shop.wannab.frontservice.order.list.deliveryPolicy.dto.DeliveryPolicyResponse;
import shop.wannab.frontservice.order.dto.*;
import shop.wannab.frontservice.order.list.orderDetail.dto.OrderDetailResponse;
import shop.wannab.frontservice.order.list.ordersManagement.dto.OrderListResponse;
import shop.wannab.frontservice.order.list.ordersManagement.dto.OrderStatus;
import shop.wannab.frontservice.order.list.ordersManagement.dto.PageResponse;
import shop.wannab.frontservice.order.list.wrappingPolicy.dto.WrappingPaperRequest;
import shop.wannab.frontservice.order.list.wrappingPolicy.dto.WrappingPaperResponse;

@FeignClient(name = "order-payment-service", url = "http://localhost:8080")
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




    //배송비정책 CRUD
    @PostMapping("/api/admin/delivery-policy")
    DeliveryPolicyResponse deliveryPolicyCreate(@RequestBody DeliveryPolicyRequest request);

    @PutMapping("/api/admin/delivery-policy/{dp-id}")
    DeliveryPolicyResponse deliveryPolicyUpdate(@PathVariable("dp-id") Long id,
                                                @RequestBody DeliveryPolicyRequest request);

    @DeleteMapping("/api/admin/delivery-policy/{dp-id}")
    void deliveryPolicyDelete(@PathVariable("dp-id") Long id);

    @GetMapping("/api/admin/delivery-policy")
    List<DeliveryPolicyResponse> deliveryPolicyfindAll();


    //포장지정책 CRUD
    @PostMapping("/api/admin/wrapping-papers")
    WrappingPaperResponse wrappingPaperCreate(@RequestBody WrappingPaperRequest request);

    @PutMapping("/api/admin/wrapping-papers/{wp-id}")
    WrappingPaperResponse wrappingPaperUpdate(@PathVariable("wp-id") Long id,
                                              @RequestBody WrappingPaperRequest request);

    @DeleteMapping("/api/admin/wrapping-papers/{wp-id}")
    void wrappingPaperDelete(@PathVariable("wp-id") Long id);

    @GetMapping("/api/admin/wrapping-papers")
    List<WrappingPaperResponse> wrappingfindAll();


    //주문관리
    @GetMapping("/api/orders/all")
    PageResponse<OrderListResponse> getAllOrders(@RequestHeader("X-User-Id") Long userId,
                                                 @RequestParam int page,
                                                 @RequestParam int size);

    @PatchMapping("/api/orders/{orderId}/status")
    void updateOrderStatus(@RequestHeader("X-User-Id") Long userId,
                           @PathVariable("orderId") Long orderId,
                           @RequestParam("newStatus") OrderStatus orderStatus);


    //주문상세조회

    //회원
    @GetMapping("/api/orders/{orderId}")
    OrderDetailResponse getOrderDetail(@RequestHeader("X-User-Id") Long userId,
                                       @PathVariable("orderId") Long orderId);
    //비회원
    @GetMapping("/api/orders/guest")
    OrderDetailResponse getGuestOrderDetail(@RequestParam Long orderId,
                                            @RequestParam String password);


}
