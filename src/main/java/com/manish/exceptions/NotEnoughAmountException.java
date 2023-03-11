package com.manish.exceptions;

public class NotEnoughAmountException extends RuntimeException {
    public NotEnoughAmountException() {
    }

    public NotEnoughAmountException(String message) {
        super(message);
    }

    public NotEnoughAmountException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughAmountException(Throwable cause) {
        super(cause);
    }

    public NotEnoughAmountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
