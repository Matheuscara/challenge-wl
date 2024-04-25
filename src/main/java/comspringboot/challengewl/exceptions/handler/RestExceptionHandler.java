package comspringboot.challengewl.exceptions.handler;

import comspringboot.challengewl.exceptions.UserConflictException;
import comspringboot.challengewl.exceptions.UserNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

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

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(UserConflictException.class)
    public ExceptionFilters userConflictException(final UserConflictException ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .status(CONFLICT.value())
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler
    public ExceptionFilters handleException(final Exception ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .status(BAD_REQUEST.value())
                .build();
    }
}
