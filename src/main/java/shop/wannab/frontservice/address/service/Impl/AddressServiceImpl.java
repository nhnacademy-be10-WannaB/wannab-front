package shop.wannab.frontservice.address.service.Impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
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
        Response response = userClient.getAllAddresses();

        return (List<AddressResponse>) response.getData();
    }

    @Override
    public AddressResponse findByUserId(Long addressId) {
        Response response = userClient.getAddress(addressId);
        return (AddressResponse) response.getData();
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
