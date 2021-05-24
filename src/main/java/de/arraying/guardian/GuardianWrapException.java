package de.arraying.guardian;

public class GuardianWrapException extends RuntimeException {

    public GuardianWrapException(String message) {
        super(message);
    }

    public GuardianWrapException(String message, Throwable cause) {
        super(message, cause);
    }

}
