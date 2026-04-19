package com.visa.transaction.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountNotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        String message = "Account missing";

        AccountNotFoundException ex = new AccountNotFoundException(message);

        assertEquals(message, ex.getMessage());
    }

    @Test
    void shouldBeInstanceOfRuntimeException() {
        AccountNotFoundException ex =
                new AccountNotFoundException("test");

        assertTrue(ex instanceof RuntimeException);
    }
}