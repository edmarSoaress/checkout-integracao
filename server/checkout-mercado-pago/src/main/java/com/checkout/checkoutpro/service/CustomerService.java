package com.checkout.checkoutpro.service;

import com.checkout.checkoutpro.controller.request.CustomerRequestDto;
import com.checkout.checkoutpro.mapper.CustomerMapper;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.customer.CustomerCardClient;
import com.mercadopago.client.customer.CustomerClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.customer.Customer;
import com.mercadopago.resources.customer.CustomerCard;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomerService {
    @Value(value = "${acess.token}")
    private String acessToken;

    public Customer generate(CustomerRequestDto dto) {
        MercadoPagoConfig.setAccessToken(acessToken);

        var customerClient = new CustomerClient();
        var customerCardClient = new CustomerCardClient();

        var customerRequest = CustomerMapper.convertFrom(dto);
        var customerCardCreateRequest = CustomerMapper.convertToCustomerCardFrom(dto);

        try {
            Customer customer = customerClient.create(customerRequest);
            customerCardClient.create(customer.getId(), customerCardCreateRequest);

            return customer;
        } catch (MPException e) {
            throw new RuntimeException(e);
        } catch (MPApiException e) {
            throw new RuntimeException(e.getApiResponse().getContent());
        }
    }

    public MPResultsResourcesPage searchCustomer(Map<String, Object> filters) {
        MercadoPagoConfig.setAccessToken(acessToken);

        CustomerClient client = new CustomerClient();

        MPSearchRequest searchRequest =
                MPSearchRequest.builder().offset(0).limit(0).filters(filters).build();

        try {
            return client.search(searchRequest);
        } catch (MPException e) {
            throw new RuntimeException(e);
        } catch (MPApiException e) {
            throw new RuntimeException(e.getApiResponse().getContent());
        }
    }

    public MPResourceList<CustomerCard> getCards(final String customerId) {
        MercadoPagoConfig.setAccessToken(acessToken);

        CustomerClient customerClient = new CustomerClient();

        try {
            Customer customer = customerClient.get(customerId);
            return customerClient.listCards(customer.getId());
        } catch (MPException e) {
            throw new RuntimeException(e);
        } catch (MPApiException e) {
            throw new RuntimeException(e);
        }
    }
}
