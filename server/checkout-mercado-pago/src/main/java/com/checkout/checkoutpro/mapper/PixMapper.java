package com.checkout.checkoutpro.mapper;

import com.checkout.checkoutpro.controller.request.PaymentPixRequestDto;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PixMapper {
    public static PaymentCreateRequest convertFrom(PaymentPixRequestDto dto) {

        var paymentCreateRequest =
                PaymentCreateRequest.builder()
                        .transactionAmount(dto.getTransactionAmount())
                        .description(dto.getDescription())
                        .paymentMethodId(dto.getPaymentMethodId())
                        //.dateOfExpiration(OffsetDateTime.of(2023, 1, 10, 10, 10, 10, 0, ZoneOffset.UTC))
                        .payer(
                                PaymentPayerRequest.builder()
                                        .email(dto.getEmail())
                                        .firstName(dto.getFirstName())
                                        .lastName(dto.getLastName())
                                        .identification(
                                                IdentificationRequest.builder()
                                                        .type(dto.getIdentificationType())
                                                        .number(dto.getIdentificationNumber())
                                                        .build())
                                        .build())
                        .build();

        return paymentCreateRequest;
    }
}
