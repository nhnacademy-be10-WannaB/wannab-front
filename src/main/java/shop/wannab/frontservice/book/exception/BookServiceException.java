package shop.wannab.frontservice.book.exception;

import lombok.Getter;

@Getter
public class BookServiceException extends RuntimeException {

    private final int status;
    private final String responseBody;

    public BookServiceException(String message, int status, String responseBody) {
        super(message);
        this.status = status;
        this.responseBody = responseBody;
    }
}
