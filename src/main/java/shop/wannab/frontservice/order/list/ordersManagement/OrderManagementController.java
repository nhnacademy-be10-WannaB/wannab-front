package shop.wannab.frontservice.order.list.ordersManagement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.wannab.frontservice.order.client.OrderApiClient;
import shop.wannab.frontservice.order.list.ordersManagement.dto.OrderListResponse;
import shop.wannab.frontservice.order.list.ordersManagement.dto.OrderStatus;
import shop.wannab.frontservice.order.list.ordersManagement.dto.PageResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/order")
public class OrderManagementController {

    private final OrderApiClient orderApiClient;

    @GetMapping
    public String orderPage(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "20") int size,
                            @CookieValue("X-User-Id") Long userId,
                            Model model){

        PageResponse<OrderListResponse> response = orderApiClient.getAllOrders(userId, page, size);
        model.addAttribute("orders", response.getContent());
        model.addAttribute("page", response);

        return "admin/order";
    }

    @PostMapping("/status/update")
    public String updateOrderStatus(@RequestParam Long orderId,
                                    @RequestParam String newStatus,
                                    @CookieValue("X-User-Id") Long userId,
                                    RedirectAttributes redirectAttributes) {

        OrderStatus status = OrderStatus.valueOf(newStatus);
        orderApiClient.updateOrderStatus(userId, orderId, status);
        redirectAttributes.addFlashAttribute("message", "주문 상태가 변경되었습니다.");
        return "redirect:/admin/order";
    }
}
