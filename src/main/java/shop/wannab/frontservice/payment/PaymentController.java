package shop.wannab.frontservice.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.wannab.frontservice.order.client.OrderApiClient;
import shop.wannab.frontservice.payment.dto.FinalOrderResultDto;
import shop.wannab.frontservice.payment.dto.PaymentFailResponseDto;
import shop.wannab.frontservice.payment.dto.TossConfirmRequestDto;

@Controller
@RequestMapping("/user/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final OrderApiClient orderApiClient;
    private final ObjectMapper objectMapper;

    @GetMapping("/success")
    public String paymentSuccess(
            @RequestParam String paymentKey,
            @RequestParam String orderId,
            @RequestParam int amount,
            Model model) {

        try {
            TossConfirmRequestDto requestDto = new TossConfirmRequestDto(paymentKey, orderId, amount);
            FinalOrderResultDto result = orderApiClient.confirmAndProcessPayment(requestDto);

            model.addAttribute("orderInfo", result);
            return "user/payment-success";

        } catch (FeignException e) {
            PaymentFailResponseDto failInfo = null;
            try {
                failInfo = objectMapper.readValue(e.contentUTF8(), PaymentFailResponseDto.class);
            } catch (Exception parseException) {
                failInfo = new PaymentFailResponseDto(
                        String.valueOf(e.status()),
                        "결제 서비스에서 오류가 발생했지만, 상세 정보를 파싱할 수 없습니다: " + parseException.getMessage(),
                        orderId,
                        paymentKey
                );
            }
            model.addAttribute("failInfo", failInfo);
            return "user/payment-fail";

        } catch(Exception e){
            model.addAttribute("failInfo", new PaymentFailResponseDto(
                    "UNEXPECTED_ERROR",
                    "예상치 못한 시스템 오류가 발생했습니다: " + e.getMessage(),
                    orderId,
                    paymentKey
            ));
            return "user/payment-fail";
        }

    }

    @GetMapping("/fail")
    public String paymentFail(@RequestParam String code,
                              @RequestParam String message,
                              @RequestParam String orderId,
                              Model model) {
        model.addAttribute("failInfo", new PaymentFailResponseDto(code, message, orderId, null));
        return "user/payment-fail";
    }
}
