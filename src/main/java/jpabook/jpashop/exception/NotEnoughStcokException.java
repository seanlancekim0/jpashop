package jpabook.jpashop.exception;

public class NotEnoughStcokException extends RuntimeException {
    public NotEnoughStcokException() {
        super();
    }

    public NotEnoughStcokException(String message) {
        super(message);
    }

    public NotEnoughStcokException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStcokException(Throwable cause) {
        super(cause);
    }

    protected NotEnoughStcokException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
