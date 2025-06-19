package shop.wannab.frontservice.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import shop.wannab.frontservice.order.dto.OrderItemListDto;
import shop.wannab.frontservice.order.dto.OrderPageRequestDto;

@FeignClient(name = "order-payment-service", url = "${order-payment.api.url}")
public interface OrderApiClient {

    @PostMapping("/api/orders")
    OrderPageRequestDto getNecesaryOrderInfo(@RequestHeader("X-User-Id") Long userId, @RequestBody OrderItemListDto orderItemListDto);
}
