package comspringboot.challengewl.exceptions.handler;

import comspringboot.challengewl.exceptions.UserNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ExceptionFilters userNotFoundException(final UserNotFoundException ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .status(NOT_FOUND.value())
                .build();
    }
}
