package shop.wannab.frontservice.user.dto;

public record LoginRequest(
        String username,
        String password
) {
}
