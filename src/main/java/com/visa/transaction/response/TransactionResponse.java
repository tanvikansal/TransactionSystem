package com.visa.transaction.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TransactionResponse {

    private UUID transactionId;

    private UUID accountId;

    private Integer operationTypeId;

    private BigDecimal amount;

    private String eventDate;
}
