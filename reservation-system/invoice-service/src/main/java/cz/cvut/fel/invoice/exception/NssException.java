package cz.cvut.fel.invoice.exception;

public class NssException extends RuntimeException {

    public NssException() {
    }

    public NssException(String message) {
        super(message);
    }

    public NssException(String message, Throwable cause) {
        super(message, cause);
    }

    public NssException(Throwable cause) {
        super(cause);
    }
}

