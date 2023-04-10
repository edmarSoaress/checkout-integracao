package com.checkout.checkoutpro.controller;

import com.checkout.checkoutpro.controller.request.PreferenceRequestDto;
import com.checkout.checkoutpro.service.CheckoutService;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/preference")
@CrossOrigin("*")
public class PreferenceController {

    @Autowired
    private CheckoutService service;

    @PostMapping
    public Preference generateExperiencia(@RequestBody PreferenceRequestDto preference){
        return service.preference(preference);
    }
}
