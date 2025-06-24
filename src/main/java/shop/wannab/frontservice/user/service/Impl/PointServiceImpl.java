package shop.wannab.frontservice.user.service.Impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.user.client.PointClient;
import shop.wannab.frontservice.user.dto.PointPageResponse;
import shop.wannab.frontservice.user.dto.PointPolicyCreateForm;
import shop.wannab.frontservice.user.dto.PointPolicyUpdateForm;
import shop.wannab.frontservice.user.service.PointService;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
    private final PointClient pointClient;

    @Override
    public List<PointPageResponse> readPointPolicy() {
        return pointClient.readRewardRates("ADMIN");
    }

    public void updatePointPolicy(PointPolicyUpdateForm pointPolicyUpdateForm) {
        pointClient.updateRewardRate("ADMIN", pointPolicyUpdateForm);
    }

    @Override
    public void createPointPolicy(PointPolicyCreateForm pointPolicyCreateForm) {
        pointClient.createRewardRate(pointPolicyCreateForm, "ADMIN");
    }
}
//public class asdf implements ResponseInterceptor
