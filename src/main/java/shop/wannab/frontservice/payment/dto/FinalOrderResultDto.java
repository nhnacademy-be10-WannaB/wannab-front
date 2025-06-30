package shop.wannab.frontservice.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinalOrderResultDto {
    private String paymentKey;
    private String orderId;
    private int amount;
}
