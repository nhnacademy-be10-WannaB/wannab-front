package shop.wannab.frontservice.address;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.*;
import shop.wannab.frontservice.address.dto.*;

import java.util.List;

@FeignClient(name = "user-service", url = "${user.api.url}")
@RequestMapping("/api/users/addresses")
public interface AddressClient {

    @GetMapping
    List<AddressResponse> getAllAddresses();

    @GetMapping("/{address-id}")
    AddressResponse getAddress(@PathVariable("address-id") Long addressId);

    @PostMapping
    AddressResponse createAddress(@RequestBody AddressCreateRequest request);

    @PutMapping("/{address-id}")
    AddressResponse updateAddress(@PathVariable("address-id") Long addressId,
                                      @RequestBody AddressUpdateRequest request);

    @DeleteMapping("/{address-id}")
    void deleteAddress(@PathVariable("address-id") Long addressId);
}
