package com.alibaba.sample.petstore.biz;

public class AdminManagerException extends RuntimeException {
    private static final long serialVersionUID = 8897703961393145194L;

    public AdminManagerException() {
    }

    public AdminManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdminManagerException(String message) {
        super(message);
    }

    public AdminManagerException(Throwable cause) {
        super(cause);
    }
}
