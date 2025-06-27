package shop.wannab.frontservice.user.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shop.wannab.frontservice.address.dto.AddressCreateRequest;
import shop.wannab.frontservice.address.dto.AddressResponse;
import shop.wannab.frontservice.address.dto.AddressUpdateRequest;
import shop.wannab.frontservice.user.dto.PointPageResponse;
import shop.wannab.frontservice.user.dto.PointPolicyCreateForm;
import shop.wannab.frontservice.user.dto.PointPolicyUpdateForm;
import shop.wannab.frontservice.user.dto.UserCreateRequest;
import shop.wannab.frontservice.user.dto.UserPageResponse;
import shop.wannab.frontservice.user.dto.UserUpdateRequest;

@FeignClient(name = "gateway", path = "/user-service", contextId = "userClient")
public interface UserClient {

    @PostMapping("/api/users")
    ResponseEntity<UserPageResponse> createUser(@RequestBody UserCreateRequest dto);

    @GetMapping("/api/users")
    ResponseEntity<UserPageResponse> readUser();

    @PatchMapping("/api/users")
    ResponseEntity<UserPageResponse> updateUser(@RequestBody UserUpdateRequest userUpdateRequest);

    @DeleteMapping("/api/users")
    ResponseEntity<Void> deleteUser();

    @GetMapping("/api/users/addresses")
    List<AddressResponse> getAllAddresses();

    @GetMapping("/api/users/addresses/{address-id}")
    AddressResponse getAddress(@PathVariable("address-id") Long addressId);

    @PostMapping("/api/users/addresses")
    AddressResponse createAddress(@RequestBody AddressCreateRequest request);

    @PutMapping("/api/users/addresses/{address-id}")
    AddressResponse updateAddress(@PathVariable("address-id") Long addressId,
                                  @RequestBody AddressUpdateRequest request);

    @DeleteMapping("/api/users/addresses/{address-id}")
    void deleteAddress(@PathVariable("address-id") Long addressId);

    @PutMapping("/api/reward-rates")
    void updateRewardRate(@RequestBody PointPolicyUpdateForm pointPolicyUpdateForm);

    @PostMapping("/api/reward-rates")
    void createRewardRate(@RequestBody PointPolicyCreateForm pointPolicyCreateForm);

    @GetMapping("/api/reward-rates")
    List<PointPageResponse> readRewardRates();
}
