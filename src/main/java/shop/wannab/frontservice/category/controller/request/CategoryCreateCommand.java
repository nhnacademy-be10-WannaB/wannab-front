package shop.wannab.frontservice.category.controller.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreateCommand(
        @NotBlank String name
) {
}
