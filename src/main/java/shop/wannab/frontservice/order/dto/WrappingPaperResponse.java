package shop.wannab.frontservice.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WrappingPaperResponse {
    private Long wpId = 0L; // 포장지 옵션 ID
    private String name = "없음"; // 포장지 이름
    private int price = 0; // 포장지 가격
}
