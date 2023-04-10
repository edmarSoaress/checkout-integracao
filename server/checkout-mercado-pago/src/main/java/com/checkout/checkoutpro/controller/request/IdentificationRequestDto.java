package com.checkout.checkoutpro.controller.request;

import lombok.Data;

@Data
public class IdentificationRequestDto {
    private String id;
    private String type;
    private String number;
}
