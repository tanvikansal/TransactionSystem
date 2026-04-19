package com.visa.transaction.operations;

import com.visa.transaction.exception.OperationTypeNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationTypeStrategyFactoryTest {

    @Test
    void shouldReturnDebitStrategyForType1() {
        DebitOperationStrategy debit = new DebitOperationStrategy();
        CreditOperationStrategy credit = new CreditOperationStrategy();

        OperationTypeStrategyFactory factory =
                new OperationTypeStrategyFactory(debit, credit);

        OperationTypeStrategy strategy = factory.getStrategy(1);

        assertTrue(strategy instanceof DebitOperationStrategy);
    }

    @Test
    void shouldReturnDebitStrategyForType2() {
        DebitOperationStrategy debit = new DebitOperationStrategy();
        CreditOperationStrategy credit = new CreditOperationStrategy();

        OperationTypeStrategyFactory factory =
                new OperationTypeStrategyFactory(debit, credit);

        OperationTypeStrategy strategy = factory.getStrategy(2);

        assertTrue(strategy instanceof DebitOperationStrategy);
    }

    @Test
    void shouldReturnDebitStrategyForType3() {
        DebitOperationStrategy debit = new DebitOperationStrategy();
        CreditOperationStrategy credit = new CreditOperationStrategy();

        OperationTypeStrategyFactory factory =
                new OperationTypeStrategyFactory(debit, credit);

        OperationTypeStrategy strategy = factory.getStrategy(3);

        assertTrue(strategy instanceof DebitOperationStrategy);
    }

    @Test
    void shouldReturnCreditStrategyForType4() {
        DebitOperationStrategy debit = new DebitOperationStrategy();
        CreditOperationStrategy credit = new CreditOperationStrategy();

        OperationTypeStrategyFactory factory =
                new OperationTypeStrategyFactory(debit, credit);

        OperationTypeStrategy strategy = factory.getStrategy(4);

        assertTrue(strategy instanceof CreditOperationStrategy);
    }

    @Test
    void shouldThrowExceptionForInvalidType() {
        DebitOperationStrategy debit = new DebitOperationStrategy();
        CreditOperationStrategy credit = new CreditOperationStrategy();

        OperationTypeStrategyFactory factory =
                new OperationTypeStrategyFactory(debit, credit);

        assertThrows(OperationTypeNotFoundException.class,
                () -> factory.getStrategy(99));
    }
}