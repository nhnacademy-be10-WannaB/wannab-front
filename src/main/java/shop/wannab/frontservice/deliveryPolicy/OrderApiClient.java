package shop.wannab.frontservice.deliveryPolicy;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shop.wannab.frontservice.deliveryPolicy.dto.DeliveryPolicyRequest;
import shop.wannab.frontservice.deliveryPolicy.dto.DeliveryPolicyResponse;

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


}
