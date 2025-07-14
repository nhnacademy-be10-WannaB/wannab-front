package shop.wannab.frontservice.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import shop.wannab.frontservice.order.list.paving.dto.PavingResponse;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderPageRequestDto {
    private OrderBookInfoListDto orderBookInfoListDto;
    private List<UserAddressResponse> userAddressList;
    private List<PavingResponse> pavingList;
    private int totalBookPrice;
    private int shippingFee;
    private int userPoints;
    private List<OrderCouponDto> orderCoupons;
    private long customerId;
}
