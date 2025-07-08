package shop.wannab.frontservice.auth.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Response<T> {
    private T data;
    private String responseCode;
    private String message;
}
