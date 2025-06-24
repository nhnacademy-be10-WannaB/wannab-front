package shop.wannab.frontservice.user.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import shop.wannab.frontservice.user.dto.PointPageResponse;
import shop.wannab.frontservice.user.dto.PointPolicyCreateForm;
import shop.wannab.frontservice.user.dto.PointPolicyUpdateForm;

@FeignClient(name = "point-service", url = "localhost:8082")
public interface PointClient {
    @PutMapping("/api/reward-rates")
    void updateRewardRate(@RequestHeader(name = "X-USER-ROLE") String userRole,
                                 @RequestBody PointPolicyUpdateForm pointPolicyUpdateForm);

    @PostMapping("/api/reward-rates")
    void createRewardRate(@RequestBody PointPolicyCreateForm pointPolicyCreateForm,
                                 @RequestHeader(name = "X-USER-ROLE") String userRole);

    @GetMapping("/api/reward-rates")
    List<PointPageResponse> readRewardRates(@RequestHeader(name = "X-USER-ROLE") String userRole);
}
