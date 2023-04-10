package com.checkout.checkoutpro.controller.request;

import lombok.Data;

@Data
public class PayerRequestDto {

    private String email;
    private String firstName;
    private IdentificationRequestDto identification;
}
