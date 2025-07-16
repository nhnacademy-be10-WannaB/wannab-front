package shop.wannab.frontservice.auth.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import shop.wannab.frontservice.auth.controller.request.LoginRequest;
import shop.wannab.frontservice.auth.controller.request.ReissueRequest;
import shop.wannab.frontservice.auth.controller.request.TokenRequest;
import shop.wannab.frontservice.auth.controller.request.UnlockRequest;
import shop.wannab.frontservice.auth.controller.response.LoginResponse;
import shop.wannab.frontservice.auth.controller.response.ReissueResponse;
import shop.wannab.frontservice.auth.domain.PaycoLoginRequest;
import shop.wannab.frontservice.auth.domain.PaycoLoginResponse;
import shop.wannab.frontservice.auth.domain.Response;
import shop.wannab.frontservice.auth.domain.TokenPayloadRequest;
import shop.wannab.frontservice.auth.domain.TokenPayloadResponse;
import shop.wannab.frontservice.auth.domain.User;
import shop.wannab.frontservice.user.dto.UserCreateRequest;

@FeignClient(name = "gateway", url = "${gateway.api.url}", path = "/user-service", contextId = "authClient")
public interface AuthClient {

    @PostMapping("/api/auth/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request);

    @PostMapping("/api/auth/reissue")
    ResponseEntity<ReissueResponse> reissue(@RequestBody ReissueRequest request);

    @PostMapping("/api/auth/signup")
    Response<Void> createUser(@RequestBody UserCreateRequest dto);

    @GetMapping("/api/auth/users")
    User getUsers(@RequestParam("loginId") String loginId);

    @PostMapping("/api/auth/token")
    LoginResponse getToken(@RequestBody TokenRequest tokenRequest);

    @PostMapping("/api/auth/login/payco")
    ResponseEntity<Response<PaycoLoginResponse>> paycoLogin(@RequestBody PaycoLoginRequest request);

    @PostMapping("/api/auth/unlock/request")
    ResponseEntity unlockRequest(@RequestBody String userId);

    @PostMapping("/api/auth/unlock/verify")
    boolean unlockVerifiy(@RequestBody UnlockRequest unlockRequest);

    @GetMapping("/api/auth/duplicated")
    Response<Boolean> duplicatedId(@RequestParam("id") String id);

    @GetMapping("/api/users/logout")
    ResponseEntity<User> logout();

    @PostMapping("/api/auth/info")
    ResponseEntity<TokenPayloadResponse> getTokenPayload(@RequestBody TokenPayloadRequest request);
}
