package shop.wannab.frontservice.global.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private String status;
    private T data;
    private ErrorResponse error;
}
