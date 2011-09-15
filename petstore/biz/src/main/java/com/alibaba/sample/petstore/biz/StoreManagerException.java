package com.alibaba.sample.petstore.biz;

public class StoreManagerException extends RuntimeException {
    private static final long serialVersionUID = 8897703961393145194L;

    public StoreManagerException() {
    }

    public StoreManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public StoreManagerException(String message) {
        super(message);
    }

    public StoreManagerException(Throwable cause) {
        super(cause);
    }
}
