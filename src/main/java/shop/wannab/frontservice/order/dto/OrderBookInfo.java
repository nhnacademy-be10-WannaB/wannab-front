package shop.wannab.frontservice.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderBookInfo {
    private long bookId;
    private String title;
    private int originPrice;
    private int salesPrice;
    private int quantity;
}
