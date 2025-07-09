package shop.wannab.frontservice.tag.controller.request;

import jakarta.validation.constraints.NotBlank;

public record TagCreateRequest(
        @NotBlank String name
) {
}
