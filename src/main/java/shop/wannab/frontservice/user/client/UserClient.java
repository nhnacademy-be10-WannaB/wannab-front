package shop.wannab.frontservice.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
}
