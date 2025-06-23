package shop.wannab.frontservice.user.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record PointPolicyCreateForm(@NotBlank String name,
                                    @PositiveOrZero @Max(100) int addRate,
                                    @PositiveOrZero int addPoint) {
}

