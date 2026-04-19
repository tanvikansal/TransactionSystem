package com.visa.transaction.operations;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CreditOperationStrategyTest {

    private final CreditOperationStrategy strategy =
            new CreditOperationStrategy();

    @Test
    void shouldReturnSameAmountForCreditOperation() {
        BigDecimal input = new BigDecimal("100.50");

        BigDecimal result = strategy.apply(input);

        assertEquals(input, result);
    }

    @Test
    void shouldHandleZeroAmount() {
        BigDecimal input = BigDecimal.ZERO;

        BigDecimal result = strategy.apply(input);

        assertEquals(BigDecimal.ZERO, result);
    }
}