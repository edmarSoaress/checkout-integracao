package com.checkout.checkoutpro.service;

import com.checkout.checkoutpro.controller.request.PaymentPixRequestDto;
import com.checkout.checkoutpro.mapper.PaymentMapper;
import com.checkout.checkoutpro.mapper.PixMapper;
import com.checkout.checkoutpro.controller.request.PaymentRequestDto;
import com.checkout.checkoutpro.controller.request.PreferenceRequestDto;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.client.paymentmethod.PaymentMethodClient;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.paymentmethod.PaymentMethod;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CheckoutService {

    @Value(value = "${acess.token}")
    private String acessToken;
    public Preference preference(PreferenceRequestDto preference) {

        MercadoPagoConfig.setAccessToken(acessToken);

        PreferenceItemRequest itemRequest =
                PreferenceItemRequest.builder()
                        .id(preference.getId())
                        .title(preference.getTitle())
                        .description(preference.getDescription())
                        .pictureUrl(preference.getPictureUrl())
                        .categoryId(preference.getCategoryId())
                        .quantity(preference.getQuantity())
                        .currencyId("BRL")
                        .unitPrice(preference.getUnitPrice())
                        .build();

        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(itemRequest);

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items).build();

        PreferenceClient client = new PreferenceClient();

        try {
           return client.create(preferenceRequest);
        } catch (MPException e) {
            throw new RuntimeException(e);
        } catch (MPApiException e) {

            throw new RuntimeException(e);
        }
    }

    public MPResultsResourcesPage searchPayment(Map<String, Object> filters){

        MercadoPagoConfig.setAccessToken(acessToken);

        PaymentClient client = new PaymentClient();

        MPSearchRequest searchRequest =
                MPSearchRequest.builder().offset(0).limit(10).filters(filters).build();

        try {
           return client.search(searchRequest);
        } catch (MPException e) {
            throw new RuntimeException(e);
        } catch (MPApiException e) {
            throw new RuntimeException(e);
        }
    }

    public MPResourceList<PaymentMethod> getPaymentOptions() {

        MercadoPagoConfig.setAccessToken(acessToken);

        PaymentMethodClient client = new PaymentMethodClient();
        try {
            return client.list();
        } catch (MPException e) {
            throw new RuntimeException(e);
        } catch (MPApiException e) {
            throw new RuntimeException(e);
        }
    }

    public Payment applyPayment(final PaymentRequestDto paymentRequestDto){

        MercadoPagoConfig.setAccessToken(acessToken);

        PaymentClient client = new PaymentClient();

        PaymentCreateRequest paymentCreateRequest =
            PaymentCreateRequest.builder()
                .transactionAmount(paymentRequestDto.getTransactionAmount())
                .token(paymentRequestDto.getToken())
                .description(paymentRequestDto.getDescription())
                .installments(paymentRequestDto.getInstallments())
                .paymentMethodId(paymentRequestDto.getPaymentMethodId())
                .issuerId(paymentRequestDto.getIssuerId())
                .payer(PaymentPayerRequest.builder()
                        .email(paymentRequestDto.getPayer().getEmail())
//                        .identification(
//                                IdentificationRequest.builder()
//                                        .type(paymentRequestDto.getPayer().getIdentification().getType())
//                                        .number(paymentRequestDto.getPayer().getIdentification().getNumber())
//                                        .build())
                        .build())
                .build();

        try {
            return client.create(paymentCreateRequest);
        } catch (MPException e) {
            throw new RuntimeException(e);
        } catch (MPApiException e) {
            e.getApiResponse().getContent();
            throw new RuntimeException(e.getApiResponse().getContent());
        }
    }

    public Payment processPix(PaymentPixRequestDto dto){

        var pixPaymentRequest = PixMapper.convertFrom(dto);

        MercadoPagoConfig.setAccessToken(acessToken);

        PaymentClient client = new PaymentClient();

        try {
           return client.create(pixPaymentRequest);
        } catch (MPException e) {
            throw new RuntimeException(e);
        } catch (MPApiException e) {
            throw new RuntimeException(e);
        }
    }

    public Payment processSavedCard(PaymentRequestDto dto){

        var paymentCreateRequest = PaymentMapper.convertFrom(dto);

        MercadoPagoConfig.setAccessToken(acessToken);

        PaymentClient client = new PaymentClient();

        try {
            return client.create(paymentCreateRequest);
        } catch (MPException e) {
            throw new RuntimeException(e);
        } catch (MPApiException e) {
            throw new RuntimeException(e.getApiResponse().getContent());
        }
    }
}
