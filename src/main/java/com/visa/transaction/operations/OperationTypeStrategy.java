package com.visa.transaction.operations;

import java.math.BigDecimal;

public interface OperationTypeStrategy {
    BigDecimal apply(BigDecimal amount);
}
