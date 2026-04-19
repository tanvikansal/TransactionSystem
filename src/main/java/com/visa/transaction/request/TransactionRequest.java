package com.visa.transaction.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class TransactionRequest {

    private UUID accountId;

    private Integer operationTypeId;

    private BigDecimal amount;
}
