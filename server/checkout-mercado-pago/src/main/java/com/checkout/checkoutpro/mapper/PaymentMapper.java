package com.checkout.checkoutpro.mapper;

import com.checkout.checkoutpro.controller.request.PaymentRequestDto;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentMapper {
    public static PaymentCreateRequest convertFrom(PaymentRequestDto dto) {

    PaymentCreateRequest request = PaymentCreateRequest.builder()
            .transactionAmount(dto.getTransactionAmount())
            .installments(1)
            .token(dto.getToken())
            .payer(PaymentPayerRequest.builder()
                    .type("customer")
                    .id(dto.getPayer().getIdentification().getId())
                    .build())
            .build();
        return request;
    }
}
