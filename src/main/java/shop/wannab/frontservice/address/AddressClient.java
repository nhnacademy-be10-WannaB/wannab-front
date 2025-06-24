package shop.wannab.frontservice.address;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.*;
import shop.wannab.frontservice.address.dto.*;

import java.util.List;

@FeignClient(name = "user-service", url = "http://localhost:8082")
public interface AddressClient {

    @GetMapping("/api/users/addresses")
    List<AddressResponse> getAllAddresses(@RequestHeader("X-USER-ID") Long userId);

    @GetMapping("/api/users/addresses/{address-id}")
    AddressResponse getAddress(@PathVariable("address-id") Long addressId, @RequestHeader("X-USER-ID") Long userId);

    @PostMapping("/api/users/addresses")
    AddressResponse createAddress(@RequestBody AddressCreateRequest request, @RequestHeader("X-USER-ID") Long userId);

    @PutMapping("/api/users/addresses/{address-id}")
    AddressResponse updateAddress(@PathVariable("address-id") Long addressId,
                                      @RequestBody AddressUpdateRequest request, @RequestHeader("X-USER-ID") Long userId);

    @DeleteMapping("/api/users/addresses/{address-id}")
    void deleteAddress(@PathVariable("address-id") Long addressId, @RequestHeader("X-USER-ID") Long userId);
}
