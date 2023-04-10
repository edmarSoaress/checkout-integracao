package com.checkout.checkoutpro.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentPixRequestDto {

    @JsonProperty("payerFirstName")
    private String firstName;
    @JsonProperty("payerLastName")
    private String lastName;
    @NotBlank
    private String email;
    @NotNull
    private BigDecimal transactionAmount;
    private String description;
    private String identificationType;
    private String identificationNumber;
    private String paymentMethodId;
}
