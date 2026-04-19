package com.visa.transaction.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationTypeNotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        String message = "Operation missing";

        OperationTypeNotFoundException ex =
                new OperationTypeNotFoundException(message);

        assertEquals(message, ex.getMessage());
    }

    @Test
    void shouldBeInstanceOfRuntimeException() {
        OperationTypeNotFoundException ex =
                new OperationTypeNotFoundException("test");

        assertTrue(ex instanceof RuntimeException);
    }
}