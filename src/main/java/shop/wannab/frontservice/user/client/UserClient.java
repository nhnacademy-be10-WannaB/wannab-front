package shop.wannab.frontservice.user.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import shop.wannab.frontservice.address.dto.AddressCreateRequest;
import shop.wannab.frontservice.address.dto.AddressResponse;
import shop.wannab.frontservice.address.dto.AddressUpdateRequest;
import shop.wannab.frontservice.auth.domain.Response;
import shop.wannab.frontservice.category.controller.response.PageResponse;
import shop.wannab.frontservice.user.dto.PointHistoryResponse;
import shop.wannab.frontservice.user.dto.PointPageResponse;
import shop.wannab.frontservice.user.dto.PointPolicyCreateForm;
import shop.wannab.frontservice.user.dto.PointPolicyUpdateForm;
import shop.wannab.frontservice.user.dto.UserPageResponse;
import shop.wannab.frontservice.user.dto.UserUpdateRequest;

@FeignClient(name = "gateway", url = "${gateway.api.url}", path = "/user-service", contextId = "userClient")
public interface UserClient {

    @GetMapping("/api/users")
    ResponseEntity<UserPageResponse> readUser();

    @PostMapping("/api/users")
    ResponseEntity<UserPageResponse> updateUser(@RequestBody UserUpdateRequest userUpdateRequest);

    @DeleteMapping("/api/users")
    ResponseEntity<Void> deleteUser();

    @GetMapping("/api/users/addresses")
    ResponseEntity<List<AddressResponse>> getAllAddresses();

    @GetMapping("/api/users/addresses/{address-id}")
    ResponseEntity<AddressResponse> getAddress(@PathVariable("address-id") Long addressId);

    @PostMapping("/api/users/addresses")
    Response<Void> createAddress(@RequestBody AddressCreateRequest request);

    @PutMapping("/api/users/addresses/{address-id}")
    Response<Void> updateAddress(@PathVariable("address-id") Long addressId,
                                  @RequestBody AddressUpdateRequest request);

    @DeleteMapping("/api/users/addresses/{address-id}")
    Response<Void> deleteAddress(@PathVariable("address-id") Long addressId);

    @PutMapping("/api/reward-rates")
    void updateRewardRate(@RequestBody PointPolicyUpdateForm pointPolicyUpdateForm);

    @PostMapping("/api/reward-rates")
    void createRewardRate(@RequestBody PointPolicyCreateForm pointPolicyCreateForm);

    @GetMapping("/api/reward-rates")
    List<PointPageResponse> readRewardRates();

    @GetMapping("/api/users/point-histories")
    PageResponse<PointHistoryResponse> getPointHistories(
            @RequestParam("page") int page);
}
