package shop.wannab.frontservice.auth.controller.request;

public record UnlockRequest(
        String userId,
        int authenticationCode
) {
}
