package shop.wannab.frontservice.auth.controller.request;

public record LoginRequest(
        String username,
        String password
) {
}
