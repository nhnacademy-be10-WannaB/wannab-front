package shop.wannab.frontservice.address.dto;

public record AddressResponse(
        Long addressId,
        String addressName,
        String address,
        String detailAddress
        ) {
}
