package com.weekly.common.exception;


import com.weekly.common.constant.errcode.AccessErrorCode;

public class AccessException extends RuntimeException{

    private final int status;

    private final String displayMessage;

    public AccessException() {
        this(AccessErrorCode.GENERAL.code(), "访问异常");
    }

    public AccessException(int status, String displayMessage) {
        this.status = status;
        this.displayMessage = displayMessage;
    }

    public int getStatus() {
        return status;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }
}

