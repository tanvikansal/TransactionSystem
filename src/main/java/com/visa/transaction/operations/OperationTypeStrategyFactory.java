package com.visa.transaction.operations;


import com.visa.transaction.exception.OperationTypeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OperationTypeStrategyFactory {

    private final Map<Integer, OperationTypeStrategy> strategyMap;

    public OperationTypeStrategyFactory(
            DebitOperationStrategy debit,
            CreditOperationStrategy credit
    ) {
        this.strategyMap = Map.of(
                1, debit,
                2, debit,
                3, debit,
                4, credit
        );
    }

    public OperationTypeStrategy getStrategy(Integer operationTypeId) {

        OperationTypeStrategy strategy = strategyMap.get(operationTypeId);

        if (strategy == null) {
            throw new OperationTypeNotFoundException(
                    "Operation type not found: " + operationTypeId
            );
        }

        return strategy;
    }
}