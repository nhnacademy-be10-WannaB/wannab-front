package shop.wannab.frontservice.user.service.Impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.user.client.UserClient;
import shop.wannab.frontservice.user.dto.PointPageResponse;
import shop.wannab.frontservice.user.dto.PointPolicyCreateForm;
import shop.wannab.frontservice.user.dto.PointPolicyUpdateForm;
import shop.wannab.frontservice.user.service.PointService;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
    private final UserClient userClient;

    @Override
    public List<PointPageResponse> readPointPolicy() {
        return userClient.readRewardRates();
    }

    public void updatePointPolicy(PointPolicyUpdateForm pointPolicyUpdateForm) {
        userClient.updateRewardRate(pointPolicyUpdateForm);
    }

    @Override
    public void createPointPolicy(PointPolicyCreateForm pointPolicyCreateForm) {
        userClient.createRewardRate(pointPolicyCreateForm);
    }
}
