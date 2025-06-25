package shop.wannab.frontservice.address.dto;

public record AddressCreateRequest(
        String addressName,
        String address,
        String detailAddress
) {
}
