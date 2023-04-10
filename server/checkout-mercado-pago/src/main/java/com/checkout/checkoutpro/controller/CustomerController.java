package com.checkout.checkoutpro.controller;

import com.checkout.checkoutpro.controller.request.CustomerRequestDto;
import com.checkout.checkoutpro.service.CustomerService;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.resources.customer.Customer;
import com.mercadopago.resources.customer.CustomerCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/customer")
@CrossOrigin("*")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PostMapping
    public Customer genereateCustomer(@RequestBody CustomerRequestDto dto){
        return service.generate(dto);
    }

    @GetMapping
    public MPResultsResourcesPage searchCustomer(@RequestParam Map<String, Object> filters){
        return service.searchCustomer(filters);
    }

    @GetMapping("/cards/{customerId}")
    public MPResourceList<CustomerCard> getCards(@PathVariable final String customerId){
        return service.getCards(customerId);
    }
}
