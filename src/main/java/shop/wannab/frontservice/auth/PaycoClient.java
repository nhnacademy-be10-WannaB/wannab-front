package shop.wannab.frontservice.auth;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import shop.wannab.frontservice.auth.domain.PaycoUserResponse;

@FeignClient(name = "payco", url = "https://apis-payco.krp.toastoven.net")
@Headers("Content-Type: application/x-www-form-urlencoded")
public interface PaycoClient {
    @PostMapping("/payco/friends/find_member_v2.json")
    public PaycoUserResponse getPaycoUserInfo(@RequestHeader("client_id") String clientId
    , @RequestHeader("access_token") String accessToken);

}
