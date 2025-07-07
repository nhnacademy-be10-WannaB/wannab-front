package shop.wannab.frontservice.auth.domain;

public record TokenResponse(String accessToken,
                            String refreshToken) {
}
