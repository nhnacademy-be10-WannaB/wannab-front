
package shop.wannab.frontservice.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import shop.wannab.frontservice.auth.service.AuthService;
import shop.wannab.frontservice.global.config.SecurityConfig;
import shop.wannab.frontservice.global.filter.JwtAuthorizationFilter;
import shop.wannab.frontservice.order.client.OrderApiClient;
import shop.wannab.frontservice.payment.dto.FinalOrderResultDto;
import shop.wannab.frontservice.payment.dto.PaymentFailResponseDto;
import shop.wannab.frontservice.payment.dto.TossConfirmRequestDto;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("ci")
@WebMvcTest(controllers = PaymentController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthorizationFilter.class)
        })
@WithMockUser(roles = "USER")
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @MockBean
    private OrderApiClient orderApiClient;

    @Test
    @DisplayName("결제 성공 페이지 요청")
    void paymentSuccess_shouldReturnSuccessPage_whenPaymentIsConfirmed() throws Exception {
        // Given
        TossConfirmRequestDto requestDto = new TossConfirmRequestDto("test_payment_key", "test_order_id", 15000);
        FinalOrderResultDto mockResult = new FinalOrderResultDto(requestDto.getPaymentKey(), requestDto.getOrderId(), requestDto.getAmount());

        given(orderApiClient.confirmAndProcessPayment(any(TossConfirmRequestDto.class))).willReturn(mockResult);

        // When & Then
        mockMvc.perform(get("/user/toss-payments/success")
                        .param("paymentKey", requestDto.getPaymentKey())
                        .param("orderId", requestDto.getOrderId())
                        .param("amount", String.valueOf(requestDto.getAmount())))
                .andExpect(status().isOk())
                .andExpect(view().name("user/payment-success"))
                .andExpect(model().attributeExists("orderInfo"))
                .andExpect(model().attribute("orderInfo", mockResult));
    }

    @Test
    @DisplayName("결제 성공 처리 중 FeignException 발생 시 실패 페이지 반환")
    void paymentSuccess_shouldReturnFailPage_whenFeignExceptionOccurs() throws Exception {
        // Given
        TossConfirmRequestDto requestDto = new TossConfirmRequestDto("test_payment_key", "test_order_id", 15000);
        PaymentFailResponseDto failDto = new PaymentFailResponseDto("ERROR_CODE", "Error message", requestDto.getOrderId(), requestDto.getPaymentKey());
        String failDtoJson = objectMapper.writeValueAsString(failDto);

        Request request = Request.create(Request.HttpMethod.POST, "/api", Collections.emptyMap(), null, new RequestTemplate());
        FeignException feignException = new FeignException.InternalServerError("Server error", request, failDtoJson.getBytes(StandardCharsets.UTF_8), Map.of());


        given(orderApiClient.confirmAndProcessPayment(any(TossConfirmRequestDto.class))).willThrow(feignException);

        // When & Then
        mockMvc.perform(get("/user/toss-payments/success")
                        .param("paymentKey", requestDto.getPaymentKey())
                        .param("orderId", requestDto.getOrderId())
                        .param("amount", String.valueOf(requestDto.getAmount())))
                .andExpect(status().isOk())
                .andExpect(view().name("user/payment-fail"))
                .andExpect(model().attributeExists("failInfo"));
    }

    @Test
    @DisplayName("결제 실패 페이지 요청")
    void paymentFail_shouldReturnFailPage() throws Exception {
        // Given
        PaymentFailResponseDto failDto = new PaymentFailResponseDto("PAYMENT_ERROR", "결제 중 오류가 발생했습니다.", "test_order_id", null);

        // When & Then
        mockMvc.perform(get("/user/toss-payments/fail")
                        .param("code", failDto.getErrorCode())
                        .param("message", failDto.getErrorMessage())
                        .param("orderId", failDto.getOrderId()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/payment-fail"))
                .andExpect(model().attribute("failInfo", failDto));
    }

    @Test
    @DisplayName("결제 성공 요청 시 필수 파라미터 누락")
    void paymentSuccess_shouldThrowException_whenRequiredParamsAreMissing() throws Exception {
        mockMvc.perform(get("/user/toss-payments/success")
                        .param("paymentKey", "test_payment_key")
                        .param("orderId", "test_order_id"))
                .andExpect(status().isBadRequest());
    }
}
