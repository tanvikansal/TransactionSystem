package com.visa.transaction.operations;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("DEBIT")
public class DebitOperationStrategy implements OperationTypeStrategy {

    @Override
    public BigDecimal apply(BigDecimal amount) {
        return amount.negate();
    }
}
