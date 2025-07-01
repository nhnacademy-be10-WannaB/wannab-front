package shop.wannab.frontservice.order.list.orderDetail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.wannab.frontservice.order.client.OrderApiClient;
import shop.wannab.frontservice.order.list.orderDetail.dto.OrderDetailResponse;

@Controller
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderApiClient orderApiClient;

    @GetMapping("/user/main-non-member-order-detail")
    public String getGuestOrderPage(@RequestParam Long orderId,
                                    @RequestParam String password,
                                    Model model){
        OrderDetailResponse order = orderApiClient.getGuestOrderDetail(orderId, password);
        model.addAttribute("order", order);
        return "user/main-non-member-order-detail";
    }

}
