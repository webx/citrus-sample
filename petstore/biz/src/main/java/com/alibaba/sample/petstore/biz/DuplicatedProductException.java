package com.alibaba.sample.petstore.biz;

public class DuplicatedProductException extends AdminManagerException {
    private static final long serialVersionUID = 8001823899691787517L;

    public DuplicatedProductException() {
    }

    public DuplicatedProductException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedProductException(String message) {
        super(message);
    }

    public DuplicatedProductException(Throwable cause) {
        super(cause);
    }
}
