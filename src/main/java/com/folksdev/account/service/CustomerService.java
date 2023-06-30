package com.folksdev.account.service;

import com.folksdev.account.exception.CustomerNotFoundException;
import com.folksdev.account.model.Customer;
import com.folksdev.account.repository.CustomerRepository;
import org.springframework.stereotype.Service;

//SOLID (Prensibi) = (S)ingle responsibility
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    //private sadece class ici, protected sadece package ici, public her yere acik nesnelerdir
    protected Customer findCustomerById(String id){
        //findById'nin icine gidersen nesne tipi Optinal'dir. Bu beni nullPointerException'lardan kurtarir.
        return customerRepository.findById(id)
                .orElseThrow(
                        () -> new CustomerNotFoundException("Customer could not find by id: " + id));
    }
}
