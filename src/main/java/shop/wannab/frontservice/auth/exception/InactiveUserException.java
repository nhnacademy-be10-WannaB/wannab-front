package shop.wannab.frontservice.auth.exception;


import org.springframework.security.core.AuthenticationException;

public class InactiveUserException extends AuthenticationException {
    public InactiveUserException(String message) {
        super(message);
    }
}
