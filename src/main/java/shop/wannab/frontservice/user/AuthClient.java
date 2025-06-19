package shop.wannab.frontservice.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shop.wannab.frontservice.user.dto.LoginRequest;
import shop.wannab.frontservice.user.dto.LoginResponse;

@FeignClient(name = "gateway", url = "localhost:8081")
public interface AuthClient {
    @PostMapping("/user-service/api/auth/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request);

}
