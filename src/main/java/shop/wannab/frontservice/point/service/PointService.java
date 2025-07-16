package shop.wannab.frontservice.point.service;

import jakarta.validation.Valid;
import java.util.List;
import shop.wannab.frontservice.category.controller.response.PageResponse;
import shop.wannab.frontservice.point.dto.PointHistoryResponse;
import shop.wannab.frontservice.point.dto.PointPageResponse;
import shop.wannab.frontservice.point.dto.PointPolicyCreateForm;
import shop.wannab.frontservice.point.dto.PointPolicyUpdateForm;

public interface PointService {
    List<PointPageResponse> readPointPolicy();

    void updatePointPolicy(@Valid PointPolicyUpdateForm pointPolicyUpdateForm);

    void createPointPolicy(@Valid PointPolicyCreateForm pointPolicyUpdateForm);

    PageResponse<PointHistoryResponse> readPointHistories(int page);
}
