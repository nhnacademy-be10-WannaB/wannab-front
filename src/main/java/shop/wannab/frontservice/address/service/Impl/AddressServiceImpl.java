package shop.wannab.frontservice.address.service.Impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.address.dto.AddressCreateRequest;
import shop.wannab.frontservice.address.dto.AddressResponse;
import shop.wannab.frontservice.address.dto.AddressUpdateRequest;
import shop.wannab.frontservice.address.service.AddressService;
import shop.wannab.frontservice.global.response.Response;
import shop.wannab.frontservice.user.client.UserClient;
import shop.wannab.frontservice.utils.ResponseCode;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final UserClient userClient;

    @Override
    public List<AddressResponse> findAllByUserId() {
        ResponseEntity<List<AddressResponse>> response = userClient.getAllAddresses();
        List<AddressResponse> list = (List<AddressResponse>)response.getBody();
        return list;
    }

    @Override
    public AddressResponse findByUserId(Long addressId) {
        ResponseEntity response = userClient.getAddress(addressId);
        return (AddressResponse) response.getBody();
    }

    @Override
    public void save(AddressCreateRequest request) {
        userClient.createAddress(request);
    }

    @Override
    public void updateAddress(Long addressId, AddressUpdateRequest request) {
        userClient.updateAddress(addressId, request);
    }

    @Override
    public void deleteAddress(Long addressId) {
        userClient.deleteAddress(addressId);
    }
}
