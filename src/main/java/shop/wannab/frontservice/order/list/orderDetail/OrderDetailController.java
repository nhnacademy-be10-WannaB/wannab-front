package shop.wannab.frontservice.order.list.orderDetail;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.wannab.frontservice.order.client.OrderApiClient;
import shop.wannab.frontservice.order.list.orderDetail.dto.GuestOrderRequest;
import shop.wannab.frontservice.order.list.orderDetail.dto.OrderDetailResponse;
import shop.wannab.frontservice.order.list.orderDetail.dto.RefundReason;
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
     * 입력 폼
     */
    @GetMapping("/guest/main-non-member-order-form")
    public String showGuestOrderLookupPage() {
        return "guest/main-non-member-order";
    }

    /**
     * 조회 폼
     */
    @GetMapping("/guest/main-non-member-order-detail")
    public String getGuestOrderPageFromRedirect(@ModelAttribute("request") GuestOrderRequest request,
                                                Model model) {
        OrderDetailResponse order = orderApiClient.getGuestOrderDetail(request);
        model.addAttribute("order", order);
        model.addAttribute("isGuest", true);
        return "user/order-detail";
    }

    //TODO : 비밀번호 url에 표시안되게 수정하기
    /**
     비회원 주문상세조회
     */
    @PostMapping("/guest/main-non-member-order-detail")
    public String getGuestOrderPage(@ModelAttribute GuestOrderRequest request,
                                    Model model){
        OrderDetailResponse order = orderApiClient.getGuestOrderDetail(request);
        model.addAttribute("order", order);
        model.addAttribute("isGuest", true);
        return "user/order-detail";
    }

    /**회원 주문상세조회
     */
    @GetMapping("/user/mypage-order-detail")
    public String getUserOrderPage(@RequestParam Long orderId,
                                   Model model){
        OrderDetailResponse order = orderApiClient.getOrderDetail(orderId);
        model.addAttribute("order", order);
        model.addAttribute("isGuest", false);
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
                .points(user.points())
                .grade(user.grade())
                .build();
        model.addAttribute("user", viewModel);

        model.addAttribute("orders", response.getContent());
        model.addAttribute("page", response);

        return "user/mypage-order";
    }


    /**
     * 회원 주문취소
     */
    @PostMapping("user/mypage-order/cancel")
    public String userOrderCancel(@RequestParam Long orderId,
                                  RedirectAttributes redirectAttributes){

        orderApiClient.cancelOrder(orderId);
        redirectAttributes.addFlashAttribute("message", "주문취소요청이 처리되었습니다.");

        return "redirect:/user/mypage-order";
    }

    /**
     * 회원 반품
     */
    @PostMapping("user/mypage-order/refund")
    public String userOrderRefund(@RequestParam Long orderId,
                                  @RequestParam String reason,
                                  RedirectAttributes redirectAttributes){

        RefundReason refundReason = RefundReason.valueOf(reason);
        orderApiClient.refundOrder(orderId, refundReason);
        redirectAttributes.addFlashAttribute("message", "주문반품요청이 처리되었습니다.");

        return "redirect:/user/mypage-order";
    }

    //TODO : 비밀번호 url에 표시안되게 수정하기
    /**
     * 비회원 주문취소
     */
    @PostMapping("/guest/main-non-member-order-detail/cancel")
    public String guestOrderCancel(@ModelAttribute GuestOrderRequest request,
                                   RedirectAttributes redirectAttributes){
        orderApiClient.cancelGuestOrder(request);
        redirectAttributes.addFlashAttribute("message", "주문취소요청이 처리되었습니다.");
        redirectAttributes.addFlashAttribute("request", request);

        return "redirect:/guest/main-non-member-order-detail";
    }

    /**
     * 비회원 반품
     */
    @PostMapping("/guest/main-non-member-order-detail/refund")
    public String guestOrderRefund(@ModelAttribute GuestOrderRequest request,
                                   @RequestParam String reason,
                                   RedirectAttributes redirectAttributes){
        RefundReason refundReason = RefundReason.valueOf(reason);
        orderApiClient.refundGuestOrder(request, refundReason);

        redirectAttributes.addFlashAttribute("message", "주문반품요청이 처리되었습니다.");
        redirectAttributes.addFlashAttribute("request", request);

        return "redirect:/guest/main-non-member-order-detail";
    }



}
