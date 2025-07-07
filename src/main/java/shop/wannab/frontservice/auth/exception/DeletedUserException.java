package shop.wannab.frontservice.auth.exception;

public class DeletedUserException extends RuntimeException {
    public DeletedUserException(String message) {
        super(message);
    }
}
