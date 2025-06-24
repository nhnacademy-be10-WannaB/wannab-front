package shop.wannab.frontservice.address;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.*;
import shop.wannab.frontservice.address.dto.*;

import java.util.List;

@FeignClient(name = "user-service", url = "${user.api.url}")
public interface AddressClient {

    @GetMapping
    List<AddressResponse> getAllAddresses();

    @GetMapping("/api/users/addresses/{address-id}")
    AddressResponse getAddress(@PathVariable("address-id") Long addressId, @RequestHeader("X-USER-ID") String userId);

    @PostMapping("/api/users/addresses")
    AddressResponse createAddress(@RequestBody AddressCreateRequest request);

    @PutMapping("/api/users/addresses/{address-id}")
    AddressResponse updateAddress(@PathVariable("address-id") Long addressId,
                                      @RequestBody AddressUpdateRequest request);

    @DeleteMapping("/api/users/addresses/{address-id}")
    void deleteAddress(@PathVariable("address-id") Long addressId);
}
