package shop.wannab.frontservice.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookOrderSubmitDto {
    private long bookId;
    private int bookQuantity;
    private Long wrappingPaperId;
    //TODO: coupon 관련 정보 추가해야 함
}
