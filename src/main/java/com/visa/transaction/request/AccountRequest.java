package com.visa.transaction.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequest {
    @NotBlank(message = "document is required")
    @JsonProperty("document_number")
    @Pattern(regexp = "\\d+", message = "documentNumber must be numeric")
    String documentNumber;
}
