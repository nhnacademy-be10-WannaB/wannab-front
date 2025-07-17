package shop.wannab.frontservice.point.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.category.controller.response.PageResponse;
import shop.wannab.frontservice.user.client.UserClient;
import shop.wannab.frontservice.point.dto.PointHistoryResponse;
import shop.wannab.frontservice.point.dto.PointPageResponse;
import shop.wannab.frontservice.point.dto.PointPolicyCreateForm;
import shop.wannab.frontservice.point.dto.PointPolicyUpdateForm;
import shop.wannab.frontservice.point.service.PointService;

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

    @Override
    public PageResponse<PointHistoryResponse> readPointHistories(int page) {
        return userClient.getPointHistories(page);
    }
}
