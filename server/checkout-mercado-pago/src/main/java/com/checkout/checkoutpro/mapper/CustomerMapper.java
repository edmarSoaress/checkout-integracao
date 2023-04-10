package com.checkout.checkoutpro.mapper;

import com.checkout.checkoutpro.controller.request.CustomerRequestDto;
import com.checkout.checkoutpro.controller.request.PaymentPixRequestDto;
import com.mercadopago.client.customer.CustomerCardClient;
import com.mercadopago.client.customer.CustomerCardCreateRequest;
import com.mercadopago.client.customer.CustomerClient;
import com.mercadopago.client.customer.CustomerRequest;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.resources.customer.Customer;
import com.mercadopago.resources.customer.CustomerCardIssuer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerMapper {
    public static CustomerRequest convertFrom(CustomerRequestDto dto) {

        var customerRequest = CustomerRequest.builder()
                .email(dto.getEmail())
                .build();

        return customerRequest;
    }

    public static CustomerCardCreateRequest convertToCustomerCardFrom(CustomerRequestDto dto) {

        var issuer = CustomerCardIssuer.builder()
                .id(dto.getIssuer())
                .build();

        var cardCreateRequest = CustomerCardCreateRequest.builder()
                .token(dto.getToken())
               // .issuer(issuer)
                .paymentMethodId(dto.getPaymentMethodId())
                .build();

        return cardCreateRequest;
    }

}
