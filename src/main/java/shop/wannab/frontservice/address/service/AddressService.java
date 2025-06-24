package shop.wannab.frontservice.address.service;

import java.util.List;
import shop.wannab.frontservice.address.dto.AddressCreateRequest;
import shop.wannab.frontservice.address.dto.AddressResponse;
import shop.wannab.frontservice.address.dto.AddressUpdateRequest;

public interface AddressService {
    List<AddressResponse> findAllByUserId();
    
    AddressResponse findByUserId(Long addressId);

    void save(AddressCreateRequest request);
    
    void updateAddress(Long addressId, AddressUpdateRequest request);

    void deleteAddress(Long addressId);
}
