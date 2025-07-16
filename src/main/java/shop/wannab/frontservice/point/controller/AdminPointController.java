package shop.wannab.frontservice.point.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import shop.wannab.frontservice.point.dto.PointPageResponse;
import shop.wannab.frontservice.point.dto.PointPolicyCreateForm;
import shop.wannab.frontservice.point.dto.PointPolicyUpdateForm;
import shop.wannab.frontservice.point.service.PointService;

@PreAuthorize("hasRole('ADMIN')")
@Controller
@RequiredArgsConstructor
public class AdminPointController {
    private final PointService pointService;

    @GetMapping("/admin/point")
    public String point(Model model) {
        List<PointPageResponse> pointPolicies =  pointService.readPointPolicy();
        model.addAttribute("pointPolicies", pointPolicies);
        return "admin/point";
    }

    @PatchMapping("/admin/point")
    public String updatePoint(@ModelAttribute @Valid PointPolicyUpdateForm pointPolicyUpdateForm, Model model) {
        pointService.updatePointPolicy(pointPolicyUpdateForm);
        List<PointPageResponse> pointPolicies =  pointService.readPointPolicy();
        model.addAttribute("pointPolicies", pointPolicies);
        return "admin/point";
    }

    @PostMapping("/admin/point")
    public String createPoint(@ModelAttribute @Valid PointPolicyCreateForm pointPolicyCreateForm, Model model) {
        pointService.createPointPolicy(pointPolicyCreateForm);
        List<PointPageResponse> pointPolicies =  pointService.readPointPolicy();
        model.addAttribute("pointPolicies", pointPolicies);
        return "admin/point";
    }

}
