package shop.wannab.frontservice.global.response;

public record ErrorResponse(
        int status,
        int code,
        String message
) {
}
