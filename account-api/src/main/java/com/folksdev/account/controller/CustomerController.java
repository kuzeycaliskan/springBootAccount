package com.folksdev.account.controller;

import com.folksdev.account.dto.CreateCustomerRequest;
import com.folksdev.account.dto.CustomerDto;
import com.folksdev.account.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /***
     * @PathVariable ve @RequestBody farki: PathVariable URL'den alirken, @RequestBody json'dan parametre alir
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable String customerId){
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomer());
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CreateCustomerRequest request){
        return ResponseEntity.ok(customerService.createCustomer(request));
    }
}
