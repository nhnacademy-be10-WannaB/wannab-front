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
import org.springframework.web.bind.annotation.RequestHeader;
import shop.wannab.frontservice.address.dto.AddressCreateRequest;
import shop.wannab.frontservice.address.dto.AddressResponse;
import shop.wannab.frontservice.address.dto.AddressUpdateRequest;
import shop.wannab.frontservice.user.dto.PointPageResponse;
import shop.wannab.frontservice.user.dto.PointPolicyCreateForm;
import shop.wannab.frontservice.user.dto.PointPolicyUpdateForm;
import shop.wannab.frontservice.user.dto.UserCreateRequest;
import shop.wannab.frontservice.user.dto.UserPageResponse;
import shop.wannab.frontservice.user.dto.UserUpdateRequest;

@FeignClient(name = "user-service", url = "localhost:8082")
public interface UserClient {
    @PostMapping("/api/users")
//    ResponseEntity<UserResponse> createUser(@RequestBody UserCreateRequest dto);
    ResponseEntity<UserPageResponse> createUser(@RequestHeader(name = "X-USER-ID") Long userId, @RequestBody UserCreateRequest dto);

    @GetMapping("/api/users")
//    ResponseEntity<UserResponse> readUser(@PathVariable(name = "user-id") Long userId);
    ResponseEntity<UserPageResponse> readUser(@RequestHeader(name = "X-USER-ID") Long userId);

    @PatchMapping("/api/users")
//    ResponseEntity<UserResponse> updateUser(@RequestBody UserUpdateDTO userUpdateDTO);
    ResponseEntity<UserPageResponse> updateUser(@RequestHeader(name = "X-USER-ID") Long userId, @RequestBody UserUpdateRequest userUpdateRequest);

    @DeleteMapping("/api/users")
//    ResponseEntity<Void> deleteUser();
    ResponseEntity<Void> deleteUser(@RequestHeader(name = "X-USER-ID") Long userId);

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

    @PutMapping("/api/reward-rates")
    void updateRewardRate(@RequestHeader(name = "X-USER-ROLE") String userRole,
                          @RequestBody PointPolicyUpdateForm pointPolicyUpdateForm);

    @PostMapping("/api/reward-rates")
    void createRewardRate(@RequestBody PointPolicyCreateForm pointPolicyCreateForm,
                          @RequestHeader(name = "X-USER-ROLE") String userRole);

    @GetMapping("/api/reward-rates")
    List<PointPageResponse> readRewardRates(@RequestHeader(name = "X-USER-ROLE") String userRole);
}
