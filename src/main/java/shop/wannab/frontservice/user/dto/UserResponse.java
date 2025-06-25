package shop.wannab.frontservice.user.dto;

public record UserResponse(
        Long userId,
        String password,
        String username
) {
}
