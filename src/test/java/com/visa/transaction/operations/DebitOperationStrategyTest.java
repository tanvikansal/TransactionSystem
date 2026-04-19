package com.visa.transaction.operations;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DebitOperationStrategyTest {

    private final DebitOperationStrategy strategy =
            new DebitOperationStrategy();

    @Test
    void shouldReturnNegativeValueForDebit() {
        BigDecimal input = new BigDecimal("100.50");

        BigDecimal result = strategy.apply(input);

        assertEquals(new BigDecimal("-100.50"), result);
    }

    @Test
    void shouldHandleZero() {
        BigDecimal input = BigDecimal.ZERO;

        BigDecimal result = strategy.apply(input);

        assertEquals(BigDecimal.ZERO, result);
    }
}