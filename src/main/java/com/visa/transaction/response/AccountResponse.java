package com.visa.transaction.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class AccountResponse {

    @NotNull
    private String accountId;

    private Long documentNumber;

}
