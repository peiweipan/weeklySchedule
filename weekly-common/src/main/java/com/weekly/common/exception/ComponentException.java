package com.weekly.common.exception;

public class ComponentException extends RuntimeException {

    private final String displayMessage;

    public ComponentException(String message) {
        displayMessage = message;
    }

    public ComponentException(Throwable cause) {
        super(cause);

        displayMessage = "未知错误";
    }

    public ComponentException(Throwable cause, String message) {
        super(cause);
        displayMessage = message;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }
}
