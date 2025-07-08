package shop.wannab.frontservice.auth.domain;

import java.time.LocalDate;
import lombok.Builder;

public record PaycoLoginRequest(String providerId, String providerName, String name, String email, String phone,
                                LocalDate birth) {
    @Builder
    public PaycoLoginRequest {
    }
}
