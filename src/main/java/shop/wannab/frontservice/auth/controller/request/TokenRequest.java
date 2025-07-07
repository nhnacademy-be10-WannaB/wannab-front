package shop.wannab.frontservice.auth.controller.request;

import lombok.Builder;
import shop.wannab.frontservice.auth.domain.Role;


public record TokenRequest(Long userId, String role) {
    @Builder
    public TokenRequest {
    }
}
