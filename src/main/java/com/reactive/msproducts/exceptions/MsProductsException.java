package com.reactive.msproducts.exceptions;

public class MsProductsException  extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MsProductsException(Throwable cause) {
        super(cause);
    }

    public MsProductsException(String message) {
        super(message);
    }

    public MsProductsException(String message, Throwable cause) {
        super(message, cause);
    }
}
