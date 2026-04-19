package com.visa.transaction.exception;

public class OperationTypeNotFoundException extends RuntimeException {

    public OperationTypeNotFoundException(String message) {
        super(message);
    }
}
