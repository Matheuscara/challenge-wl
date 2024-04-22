package comspringboot.challengewl.exceptions;

public class UserConflictException extends RuntimeException {
    public UserConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserConflictException(String message) {
        super(message);
    }

    public UserConflictException(Throwable cause) {
        super(cause);
    }
}
