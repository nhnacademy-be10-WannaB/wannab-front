package shop.wannab.frontservice.auth.controller.response;

public record LoginResponse(String accessToken, String refreshToken) {
}
