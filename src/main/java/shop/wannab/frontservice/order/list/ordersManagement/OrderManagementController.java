package shop.wannab.frontservice.order.list.ordersManagement;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.wannab.frontservice.order.client.OrderApiClient;
import shop.wannab.frontservice.order.list.ordersManagement.dto.OrderLookupResponse;
import shop.wannab.frontservice.order.list.ordersManagement.dto.OrderStatus;
import shop.wannab.frontservice.order.list.ordersManagement.dto.PageResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/order")
public class OrderManagementController {

    private final OrderApiClient orderApiClient;

    @GetMapping
    public String orderPage(@RequestParam(required = false) Long orderId,
                            @RequestParam(required = false) String orderName,
                            @RequestParam(required = false) OrderStatus orderStatus,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "20") int size,
                            Model model) {

        PageResponse<OrderLookupResponse> response = orderApiClient.getAllOrders(
                orderId,
                orderName,
                orderStatus,
                from,
                to,
                page,
                size
        );

        model.addAttribute("orders", response.getContent());
        model.addAttribute("page", response);

        // 검색 조건 다시 뷰에 전달해서 form value에 반영
        model.addAttribute("orderId", orderId);
        model.addAttribute("orderName", orderName);
        model.addAttribute("orderStatus", orderStatus);
        model.addAttribute("from", from);
        model.addAttribute("to", to);

        return "admin/order";
    }

    @PostMapping("/status/update")
    public String updateOrderStatus(@RequestParam Long orderId,
                                    @RequestParam String newStatus,
                                    RedirectAttributes redirectAttributes) {

        OrderStatus status = OrderStatus.valueOf(newStatus);
        orderApiClient.updateOrderStatus(orderId, status);
        redirectAttributes.addFlashAttribute("message", "주문 상태가 변경되었습니다.");
        return "redirect:/admin/order";
    }
}
