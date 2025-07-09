package shop.wannab.frontservice.global.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import java.io.IOException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.wannab.frontservice.auth.domain.Response;
import shop.wannab.frontservice.book.exception.BookServiceException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookServiceException.class)
    public ResponseEntity<?> handleExternalServiceException(BookServiceException e) {

        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(Map.of(
                        "error", "도서 서비스 요청 실패",
                        "status", e.getStatus(),
                        "message", e.getMessage())
                );
    }
}
