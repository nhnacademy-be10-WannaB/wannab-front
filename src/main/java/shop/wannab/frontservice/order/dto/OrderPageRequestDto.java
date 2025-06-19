package shop.wannab.frontservice.order.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderPageRequestDto {
    private OrderBookInfoListDto orderBookInfoListDto;
    private List<UserAddressResponse> userAddressList;
    private List<WrappingPaperResponse> wrappingPaperList;
    private int totalBookPrice;
    private int shippingFee;
    private int userPoints;
}
