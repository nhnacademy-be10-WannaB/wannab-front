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
        return List.of();
    }

    @Override
    public AddressResponse findByUserId(Long addressId) {
        return null;
    }

    @Override
    public void save(AddressCreateRequest request) {

    }

    @Override
    public void updateAddress(Long addressId, AddressUpdateRequest request) {

    }

    @Override
    public void deleteAddress(Long addressId) {

    }
}
