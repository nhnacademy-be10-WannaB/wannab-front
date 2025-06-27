package shop.wannab.frontservice.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderPageRequestDto {
    private OrderBookInfoListDto orderBookInfoListDto;
    private List<UserAddressResponse> userAddressList;
    private List<WrappingPaperResponse> wrappingPaperList;
    private int totalBookPrice;
    private int shippingFee;
    private int userPoints;
    private List<OrderCouponDto> orderCoupons;
}