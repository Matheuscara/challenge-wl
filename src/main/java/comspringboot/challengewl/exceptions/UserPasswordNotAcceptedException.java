package comspringboot.challengewl.exceptions;

public class UserPasswordNotAccepted extends RuntimeException {

    public UserPasswordNotAccepted(String message) {
        super(message);
    }
}
