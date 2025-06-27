package shop.wannab.frontservice.auth.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shop.wannab.frontservice.auth.controller.request.LoginRequest;
import shop.wannab.frontservice.auth.controller.request.ReissueRequest;
import shop.wannab.frontservice.auth.controller.response.LoginResponse;
import shop.wannab.frontservice.auth.controller.response.ReissueResponse;

@FeignClient(name = "gateway", path = "/user-service", contextId = "authClient")
public interface AuthClient {

    @PostMapping("/api/auth/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request);

    @PostMapping("/api/auth/reissue")
    ResponseEntity<ReissueResponse> reissue(@RequestBody ReissueRequest request);

}
