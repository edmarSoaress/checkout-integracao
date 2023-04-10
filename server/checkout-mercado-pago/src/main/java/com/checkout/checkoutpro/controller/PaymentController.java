package com.checkout.checkoutpro.controller;

import com.checkout.checkoutpro.controller.request.PaymentPixRequestDto;
import com.checkout.checkoutpro.controller.request.PaymentRequestDto;
import com.checkout.checkoutpro.service.CheckoutService;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.paymentmethod.PaymentMethod;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payment")
@CrossOrigin("*")
public class PaymentController {

    @Autowired
    private CheckoutService service;

    @GetMapping
    public MPResultsResourcesPage searchPayment(@RequestParam Map<String, Object> filters){
        return service.searchPayment(filters);
    }

    @GetMapping("/options")
    public MPResourceList<PaymentMethod> getPaymentsOptions(){
        return service.getPaymentOptions();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Payment applyCreditCard(@RequestBody PaymentRequestDto paymentRequestDto){
        return service.applyPayment(paymentRequestDto);
    }

    @PostMapping("/offline")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Payment> proccessPix(@RequestBody @Valid PaymentPixRequestDto pixRequestDto){
        return ResponseEntity.ok(service.processPix(pixRequestDto));
    }

    @PostMapping("/savedcard")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Payment> proccessSavedCard(@RequestBody @Valid PaymentRequestDto paymentRequestDto){
        return ResponseEntity.ok(service.processSavedCard(paymentRequestDto));
    }
}
