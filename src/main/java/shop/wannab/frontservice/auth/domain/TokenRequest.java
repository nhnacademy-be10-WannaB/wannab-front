package shop.wannab.frontservice.auth.domain;

public record TokenRequest(Long userId,
                          Role role) {
}
