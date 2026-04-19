package com.visa.transaction.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Account creation request")
public class AccountRequest {
    @NotBlank(message = "document is required")
    @JsonProperty("document_number")
    @Pattern(regexp = "\\d+", message = "documentNumber must be numeric")
    @Schema(example = "123456789001")
    String documentNumber;
}
