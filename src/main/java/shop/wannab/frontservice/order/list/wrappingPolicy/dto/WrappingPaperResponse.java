package shop.wannab.frontservice.order.list.wrappingPolicy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WrappingPaperResponse {
    /**
     * 포장지 옵션 ID
     */
    private Long wpId;

    /**
     * 포장지 이름
     */
    private String name;

    /**
     * 포장지 가격
     */
    private int price;
}
