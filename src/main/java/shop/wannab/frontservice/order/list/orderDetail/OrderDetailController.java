package shop.wannab.frontservice.order.list.orderDetail;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.wannab.frontservice.order.client.OrderApiClient;
import shop.wannab.frontservice.order.list.orderDetail.dto.OrderDetailResponse;
import shop.wannab.frontservice.order.list.ordersManagement.dto.OrderLookupResponse;
import shop.wannab.frontservice.order.list.ordersManagement.dto.PageResponse;
import shop.wannab.frontservice.user.dto.UserPageResponse;
import shop.wannab.frontservice.user.model.UserViewModel;
import shop.wannab.frontservice.user.service.UserService;

@Controller
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderApiClient orderApiClient;
    private final UserService userService;


    /**
     비회원 주문상세조회
     */
    @GetMapping("/user/main-non-member-order-detail")
    public String getGuestOrderPage(@RequestParam Long orderId,
                                    @RequestParam String password,
                                    Model model){
        OrderDetailResponse order = orderApiClient.getGuestOrderDetail(orderId, password);
        model.addAttribute("order", order);
        return "user/order-detail";
    }

    /**회원 주문상세조회
     */
    @GetMapping("/user/mypage-order-detail")
    public String getUserOrderPage(@RequestParam Long orderId,
                                   Model model){
        OrderDetailResponse order = orderApiClient.getOrderDetail(orderId);
        model.addAttribute("order", order);
        return "user/order-detail";
    }

    /**회원주문조회 (list)
     */
    @GetMapping("/user/mypage-order")
    public String getUserOrders(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "20") int size,
                                Model model){
        PageResponse<OrderLookupResponse> response = orderApiClient.getOrdersByUser(page, size);

        UserPageResponse user = userService.readUser();
        UserViewModel viewModel = UserViewModel.builder()
                .id(user.username())
                .password(user.password())
                .phone(user.phone())
                .birth(user.birth())
                .nickname(user.nickname())
                .email(user.email())
                .name(user.name())
                .build();
        model.addAttribute("user", viewModel);

        model.addAttribute("orders", response.getContent());
        model.addAttribute("page", response);

        return "user/mypage-order";
    }

}
