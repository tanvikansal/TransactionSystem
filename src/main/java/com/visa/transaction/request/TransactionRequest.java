package com.visa.transaction.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Schema(description = "Transaction request")
public class TransactionRequest {

    @Schema(example = "3031db51-e275-4031-81e1-ad92c222a96c")
    private UUID accountId;

    @Schema(example = "1")
    private Integer operationTypeId;

    @Schema(example = "100.0")
    private BigDecimal amount;
}
