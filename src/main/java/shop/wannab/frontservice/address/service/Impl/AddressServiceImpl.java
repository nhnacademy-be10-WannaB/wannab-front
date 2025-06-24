package shop.wannab.frontservice.address.service.Impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.address.AddressClient;
import shop.wannab.frontservice.address.dto.AddressCreateRequest;
import shop.wannab.frontservice.address.dto.AddressResponse;
import shop.wannab.frontservice.address.dto.AddressUpdateRequest;
import shop.wannab.frontservice.address.service.AddressService;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressClient addressClient;

    @Override
    public List<AddressResponse> findAllByUserId() {
        return addressClient.getAllAddresses(1l);
    }

    @Override
    public AddressResponse findByUserId(Long addressId) {
        return addressClient.getAddress(addressId, 1l);
    }

    @Override
    public void save(AddressCreateRequest request) {
        addressClient.createAddress(request, 1l);
    }

    @Override
    public void updateAddress(Long addressId, AddressUpdateRequest request) {
        addressClient.updateAddress(addressId, request, 1l);
    }

    @Override
    public void deleteAddress(Long addressId) {
        addressClient.deleteAddress(addressId, 1l);
    }
}
