package com.alibaba.sample.petstore.biz;

public class UserManagerException extends RuntimeException {
    private static final long serialVersionUID = -8196055690022375153L;

    public UserManagerException() {
        super();
    }

    public UserManagerException(String message) {
        super(message);
    }

    public UserManagerException(Throwable cause) {
        super(cause);
    }

    public UserManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
