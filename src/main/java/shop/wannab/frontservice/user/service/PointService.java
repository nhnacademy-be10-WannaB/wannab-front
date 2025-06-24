package shop.wannab.frontservice.user.service;

import jakarta.validation.Valid;
import java.util.List;
import shop.wannab.frontservice.user.dto.PointPageResponse;
import shop.wannab.frontservice.user.dto.PointPolicyCreateForm;
import shop.wannab.frontservice.user.dto.PointPolicyUpdateForm;

public interface PointService {
    List<PointPageResponse> readPointPolicy();

    void updatePointPolicy(@Valid PointPolicyUpdateForm pointPolicyUpdateForm);

    void createPointPolicy(@Valid PointPolicyCreateForm pointPolicyUpdateForm);
}
