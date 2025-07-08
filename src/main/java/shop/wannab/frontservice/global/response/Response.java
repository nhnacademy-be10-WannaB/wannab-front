package shop.wannab.frontservice.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {
    private final T data;
    private final String responseCode;
    private final String message;
}