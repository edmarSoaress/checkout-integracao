package com.checkout.checkoutpro.controller.request;

import com.checkout.checkoutpro.controller.request.PayerRequestDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequestDto {
    private String token;
    private String description;
    private String externalReference;
    private Integer installments;
    @JsonProperty("issuer_id")
    private String issuerId;
    @JsonProperty("transaction_amount")
    private BigDecimal transactionAmount;
    @JsonProperty("payment_method_id")
    private String paymentMethodId;
    private PayerRequestDto payer;
}
