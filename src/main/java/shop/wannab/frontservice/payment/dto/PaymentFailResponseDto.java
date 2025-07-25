package shop.wannab.frontservice.payment.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PaymentFailResponseDto {
    private String errorCode;
    private String errorMessage;
    private String orderId;
    private String paymentKey;
}

