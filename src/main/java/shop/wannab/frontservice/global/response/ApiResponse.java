package shop.wannab.frontservice.global.response;

public record ApiResponse<T>(
        String status,
        T data,
        ErrorResponse error
) {
}
