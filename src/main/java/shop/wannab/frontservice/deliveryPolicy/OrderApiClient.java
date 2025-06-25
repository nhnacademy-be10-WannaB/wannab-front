package shop.wannab.frontservice.deliveryPolicy;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import shop.wannab.frontservice.deliveryPolicy.dto.DeliveryPolicyRequest;
import shop.wannab.frontservice.deliveryPolicy.dto.DeliveryPolicyResponse;
import shop.wannab.frontservice.ordersManagement.dto.OrderListResponse;
import shop.wannab.frontservice.ordersManagement.dto.OrderStatus;
import shop.wannab.frontservice.ordersManagement.dto.PageResponse;
import shop.wannab.frontservice.wrappingPolicy.dto.WrappingPaperRequest;
import shop.wannab.frontservice.wrappingPolicy.dto.WrappingPaperResponse;

@FeignClient(name = "order-payment-service", url = "http://localhost:8080")
public interface OrderApiClient {


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
    PageResponse<OrderListResponse> getAllOrders(//@RequestHeader("X-User-Id") Long userId,
                                                 @RequestParam int page,
                                                 @RequestParam int size);

    @PatchMapping("/api/orders/{orderId}/status")
    void updateOrderStatus(//@RequestHeader("X-User-Id") Long userId,
                           @PathVariable("orderId") Long orderId,
                           @RequestParam("newStatus")OrderStatus orderStatus);


}
