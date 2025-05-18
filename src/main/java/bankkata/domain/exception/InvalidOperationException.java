package bankkata.domain.exception;

/**
 * Exception thrown when an invalid operation is attempted on an account.
 */
public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException(String message) {
        super(message);
    }
}
