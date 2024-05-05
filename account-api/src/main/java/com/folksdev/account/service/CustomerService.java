package com.folksdev.account.service;

import com.folksdev.account.dto.CreateCustomerRequest;
import com.folksdev.account.dto.CustomerDto;
import com.folksdev.account.dto.converter.CustomerDtoConverter;
import com.folksdev.account.exception.CustomerNotFoundException;
import com.folksdev.account.model.Customer;
import com.folksdev.account.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//SOLID (Prensibi) = (S)ingle responsibility
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private  final CustomerDtoConverter customerDtoConverter; //disariya expose edecegimiz icin converter'a ihtiyacimiz var

    public CustomerService(CustomerRepository customerRepository,
                           CustomerDtoConverter converter) {
        this.customerRepository = customerRepository;
        this.customerDtoConverter = converter;
    }

    //private sadece class ici, protected sadece package ici, public her yere acik nesnelerdir
    protected Customer findCustomerById(String id){
        //findById'nin icine gidersen nesne tipi Optional'dir. Bu beni nullPointerException'lardan kurtarir.
        return customerRepository.findById(id)
                .orElseThrow(
                        () -> new CustomerNotFoundException("Customer could not find by id: " + id));
    }

    /***
     * Zaten yukaridaki findCustomerById function'u customer id ile customer donuyor. Kod duplicate yapmamak
     * icin getCustomerById metodunu CustomerDtoConverter'dan cagirarak yapacagiz
     */
    public CustomerDto getCustomerById(String customerId) {
        return  customerDtoConverter.convertToCustomerDto(findCustomerById(customerId));
    }

    public List<CustomerDto> getAllCustomer() {
        return customerRepository.findAll().stream()
                .map(customerDtoConverter::convertToCustomerDto)
                .collect(Collectors.toList());
    }

    public CustomerDto createCustomer(CreateCustomerRequest request) {
        Customer customer;

        customer = new Customer(
                request.getCustomerName(),
                request.getCustomerSurname()
        );

        return customerDtoConverter.convertToCustomerDto(customerRepository.save(customer));
    }
}
