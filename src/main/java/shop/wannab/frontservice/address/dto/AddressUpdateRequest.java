package shop.wannab.frontservice.address.dto;

public record AddressUpdateRequest(
        String addressName,
        String address,
        String detailAddress
) {
}
