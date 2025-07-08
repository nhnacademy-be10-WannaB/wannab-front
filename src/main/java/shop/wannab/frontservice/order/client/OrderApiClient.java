package shop.wannab.frontservice.order.client;

import jakarta.servlet.http.Cookie;
import java.time.LocalDate;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.wannab.frontservice.order.list.deliveryPolicy.dto.DeliveryPolicyRequest;
import shop.wannab.frontservice.order.list.deliveryPolicy.dto.DeliveryPolicyResponse;
import shop.wannab.frontservice.order.dto.*;
import shop.wannab.frontservice.order.list.orderDetail.dto.GuestOrderRequest;
import shop.wannab.frontservice.order.list.orderDetail.dto.OrderDetailResponse;
import shop.wannab.frontservice.order.list.orderDetail.dto.RefundReason;
import shop.wannab.frontservice.order.list.ordersManagement.dto.OrderLookupResponse;
import shop.wannab.frontservice.order.list.ordersManagement.dto.OrderStatus;
import shop.wannab.frontservice.order.list.ordersManagement.dto.PageResponse;
import shop.wannab.frontservice.order.list.wrappingPolicy.dto.WrappingPaperRequest;
import shop.wannab.frontservice.order.list.wrappingPolicy.dto.WrappingPaperResponse;
import shop.wannab.frontservice.payment.dto.FinalOrderResultDto;
import shop.wannab.frontservice.payment.dto.TossConfirmRequestDto;

@FeignClient(name = "gateway", url = "${gateway.api.url}", path = "/order-payment-service", contextId = "orderApiClient")
public interface OrderApiClient {

    @PostMapping(value = "/api/cart", consumes = "application/json", produces = "application/json")
    GuestCartCookieDto createCart();

    @PostMapping("/api/orders")
    OrderPageRequestDto getNecesaryOrderInfo(@RequestParam Long guestId, @RequestBody OrderItemListDto orderItemListDto);

    @GetMapping("/api/cart")
    OrderBookInfoListDto getCartItems(@RequestParam(required = false) Long guestId);

    @PostMapping("/api/cart/books")
    OrderBookInfoListDto addProductToCart(@RequestParam(required = false) Long guestId, @RequestParam Long bookId);

    @PutMapping("/api/cart/books/{book-id}")
    OrderBookInfoListDto updateCartItemQuantity(@RequestParam(required = false) Long guestId, @PathVariable(name = "book-id") Long bookId, @RequestParam int quantity);

    @DeleteMapping("/api/cart/books/{book-id}")
    OrderBookInfoListDto removeProductFromCart(@RequestParam(required = false) Long guestId, @PathVariable(name = "book-id") Long bookId);

    @PostMapping("/api/orders/new")
    OrderInfoForPayment processOrder(@RequestParam Long guestId, @RequestBody OrderSubmitDto orderSubmitDto);
    /**
     * 배송비정책 CRUD
     */
    @PostMapping("/api/admin/delivery-policy")
    DeliveryPolicyResponse deliveryPolicyCreate(@RequestBody DeliveryPolicyRequest request);

    @PutMapping("/api/admin/delivery-policy/{dp-id}")
    DeliveryPolicyResponse deliveryPolicyUpdate(@PathVariable("dp-id") Long id,
                                                @RequestBody DeliveryPolicyRequest request);

    @DeleteMapping("/api/admin/delivery-policy/{dp-id}")
    void deliveryPolicyDelete(@PathVariable("dp-id") Long id);

    @GetMapping("/api/admin/delivery-policy")
    List<DeliveryPolicyResponse> deliveryPolicyfindAll();

    /**
     * 포장지 정책 CRUD
     */
    @PostMapping("/api/admin/wrapping-papers")
    WrappingPaperResponse wrappingPaperCreate(@RequestBody WrappingPaperRequest request);

    @PutMapping("/api/admin/wrapping-papers/{wp-id}")
    WrappingPaperResponse wrappingPaperUpdate(@PathVariable("wp-id") Long id,
                                              @RequestBody WrappingPaperRequest request);

    @DeleteMapping("/api/admin/wrapping-papers/{wp-id}")
    void wrappingPaperDelete(@PathVariable("wp-id") Long id);

    @GetMapping("/api/admin/wrapping-papers")
    List<WrappingPaperResponse> wrappingfindAll();

    /**
     * 주문 관리
     */
    @GetMapping("/api/admin/orders")
    PageResponse<OrderLookupResponse> getAllOrders(@RequestParam(required = false) Long orderId,
                                                   @RequestParam(required = false) String orderName,
                                                   @RequestParam(required = false) OrderStatus orderStatus,
                                                   @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                   @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                                                   @RequestParam int page,
                                                   @RequestParam int size);

    @PostMapping("/api/admin/orders/{orderId}")
    void updateOrderStatus(@PathVariable("orderId") Long orderId,
                           @RequestParam("newStatus") OrderStatus orderStatus);


    /**
     * 주문 상세 조회 - 회원
     */
    @GetMapping("/api/orders/{orderId}")
    OrderDetailResponse getOrderDetail(@PathVariable("orderId") Long orderId);

    /**
     * 주문 상세 조회 - 비회원
     */
    @PostMapping("/api/orders/guest")
    OrderDetailResponse getGuestOrderDetail(@RequestBody GuestOrderRequest request);

    /**회원주문목록 조회
     */
    @GetMapping("/api/orders")
    PageResponse<OrderLookupResponse> getOrdersByUser(@RequestParam int page,
                                                      @RequestParam int size);


    /**
     * 회원 주문취소
     */
    @PostMapping("/api/orders/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId);

    /**
     * 회원 반품
     */
    @PostMapping("/api/orders/{orderId}/refund")
    public ResponseEntity<Void> refundOrder(@PathVariable Long orderId,
                                            @RequestParam RefundReason reason);

    /**
     * 비회원 주문취소
     */
    @PostMapping("/api/orders/guest/cancel")
    public ResponseEntity<Void> cancelGuestOrder(@RequestBody GuestOrderRequest request);

    /**
     * 비회원 반품
     */
    @PostMapping("/api/orders/guest/refund")
    public ResponseEntity<Void> refundGuestOrder(@RequestBody GuestOrderRequest request,
                                                 @RequestParam RefundReason reason);


    /**
     * 결제 성공 시 주문/결제 서비스로 전송
     */
    @PostMapping("/api/payments/success")
    FinalOrderResultDto confirmAndProcessPayment(@RequestBody TossConfirmRequestDto requestDto);
}
