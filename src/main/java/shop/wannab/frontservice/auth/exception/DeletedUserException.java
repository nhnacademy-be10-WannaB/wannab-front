package shop.wannab.frontservice.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class DeletedUserException extends AuthenticationException {
    public DeletedUserException(String message) {
        super(message);
    }
}
