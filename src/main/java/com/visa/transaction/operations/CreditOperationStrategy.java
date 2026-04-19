package com.visa.transaction.operations;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("CREDIT")
public class CreditOperationStrategy implements OperationTypeStrategy {

    @Override
    public BigDecimal apply(BigDecimal amount) {
        return amount;
    }
}
