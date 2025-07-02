package shop.wannab.frontservice.global.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private int status;
    private int code;
    private String message;
}
