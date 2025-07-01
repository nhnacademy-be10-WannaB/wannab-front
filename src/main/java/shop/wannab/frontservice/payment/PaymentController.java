package shop.wannab.frontservice.payment;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.wannab.frontservice.order.client.OrderApiClient;
import shop.wannab.frontservice.payment.dto.FinalOrderResultDto;
import shop.wannab.frontservice.payment.dto.TossConfirmRequestDto;

@Controller
@RequestMapping("/user/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final OrderApiClient orderApiClient;

    @GetMapping("/success")
    public String paymentSuccess(
            @RequestParam String paymentKey,
            @RequestParam String orderId,
            @RequestParam int amount,
            Model model) {

        try {
            // ✅ 모든 결제 처리 로직을 주문/결제 서비스에 위임
            TossConfirmRequestDto requestDto = new TossConfirmRequestDto(paymentKey, orderId, amount);
            FinalOrderResultDto result = orderApiClient.confirmAndProcessPayment(requestDto);

            // 성공 결과에 따라 모델에 데이터 추가
            model.addAttribute("orderInfo", result);
            return "user/payment-success";

        } catch (FeignException e) {
            // 실패 처리
            model.addAttribute("message", "결제 승인 중 오류가 발생했습니다.");
            model.addAttribute("code", e.status());
            return "user/payment-fail";
        }
    }

    @GetMapping("/fail")
    public String paymentFail(@RequestParam String code, @RequestParam String message, Model model) {
        model.addAttribute("code", code);
        model.addAttribute("message", message);
        return "user/payment-fail";
    }
}
