package shop.wannab.frontservice.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserAddressResponse {
    private Long addressId;
    private String addressName;
    private String address;
    private String detailAddress;
}
