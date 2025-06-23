package shop.wannab.frontservice.user.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import shop.wannab.frontservice.user.dto.PointPageResponse;
import shop.wannab.frontservice.user.dto.PointPolicyCreateForm;
import shop.wannab.frontservice.user.dto.PointPolicyUpdateForm;
import shop.wannab.frontservice.user.service.PointService;

@Controller
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    @GetMapping("admin/point")
    public String point(Model model) {
        List<PointPageResponse> pointPolicies =  pointService.readPointPolicy();
        model.addAttribute("pointPolicies", pointPolicies);
        return "admin/point";
    }

    @PatchMapping("admin/point")
    public String updatePoint(@ModelAttribute @Valid PointPolicyUpdateForm pointPolicyUpdateForm, Model model) {
        pointService.updatePointPolicy(pointPolicyUpdateForm);
        List<PointPageResponse> pointPolicies =  pointService.readPointPolicy();
        model.addAttribute("pointPolicies", pointPolicies);
        return "admin/point";
    }

    @PostMapping("admin/point")
    public String createPoint(@ModelAttribute @Valid PointPolicyCreateForm pointPolicyCreateForm, Model model) {
        pointService.createPointPolicy(pointPolicyCreateForm);
        List<PointPageResponse> pointPolicies =  pointService.readPointPolicy();
        model.addAttribute("pointPolicies", pointPolicies);
        return "admin/point";
    }
}
