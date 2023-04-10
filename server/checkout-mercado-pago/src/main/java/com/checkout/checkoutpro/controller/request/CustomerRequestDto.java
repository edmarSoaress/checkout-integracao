package com.checkout.checkoutpro.controller.request;

import lombok.Data;

@Data
public class CustomerRequestDto {
    private String email;
    private String token;
    private String issuer;
    private String paymentMethodId;
}
