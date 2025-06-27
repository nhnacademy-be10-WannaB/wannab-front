package shop.wannab.frontservice.order.list.wrappingPolicy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class WrappingPaperRequest {

    /**
     * 포장지 이름
     */
    @NotBlank(message = "포장지 이름은 필수로 입력해야 합니다")
    private String name;

    /**
     * 포장지 가격
     */
    @Positive(message = "가격은 0보다 크게 입력해야 합니다")
    private int price;
}
