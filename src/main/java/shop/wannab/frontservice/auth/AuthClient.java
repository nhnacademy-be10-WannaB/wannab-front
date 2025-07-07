package shop.wannab.frontservice.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shop.wannab.frontservice.auth.controller.request.LoginRequest;
import shop.wannab.frontservice.auth.controller.request.ReissueRequest;
import shop.wannab.frontservice.auth.controller.response.LoginResponse;
import shop.wannab.frontservice.auth.controller.response.ReissueResponse;
import shop.wannab.frontservice.auth.domain.PaycoLoginRequest;
import shop.wannab.frontservice.auth.domain.PaycoLoginResponse;
import shop.wannab.frontservice.auth.domain.Response;
import shop.wannab.frontservice.auth.domain.TokenRequest;
import shop.wannab.frontservice.auth.domain.TokenResponse;
import shop.wannab.frontservice.user.dto.UserCreateRequest;
import shop.wannab.frontservice.user.dto.UserPageResponse;

@FeignClient(name = "gateway", url = "${gateway.api.url}", path = "/user-service", contextId = "authClient")
public interface AuthClient {

    @PostMapping("/api/auth/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request);

    @PostMapping("/api/auth/reissue")
    ResponseEntity<ReissueResponse> reissue(@RequestBody ReissueRequest request);

    @PostMapping("/api/auth/signup")
    ResponseEntity<UserPageResponse> createUser(@RequestBody UserCreateRequest dto);

    @PostMapping("/api/auth/login/payco")
    ResponseEntity<Response<PaycoLoginResponse>> paycoLogin(@RequestBody PaycoLoginRequest request);

    @GetMapping("/api/auth/token")
    ResponseEntity<TokenResponse> getToken(@RequestBody TokenRequest tokenRequest);
}
