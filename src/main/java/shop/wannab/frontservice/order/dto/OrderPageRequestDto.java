package shop.wannab.frontservice.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderPageRequestDto {
    private OrderBookInfoListDto orderBookInfoListDto;
    private List<UserAddressResponse> userAddressList;
    private List<WrappingPaperResponse> wrappingPaperList;
    private int totalBookPrice;
    private int shippingFee;
    private int userPoints;
}
