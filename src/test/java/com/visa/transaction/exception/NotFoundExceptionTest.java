package com.visa.transaction.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        String message = "Resource not found";

        NotFoundException ex = new NotFoundException(message);

        assertEquals(message, ex.getMessage());
    }

    @Test
    void shouldBeInstanceOfRuntimeException() {
        NotFoundException ex = new NotFoundException("test");

        assertTrue(ex instanceof RuntimeException);
    }
}