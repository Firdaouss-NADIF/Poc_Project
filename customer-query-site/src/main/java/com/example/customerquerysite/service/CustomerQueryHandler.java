package com.example.customerquerysite.service;


import com.example.customerquerysite.entities.Customer;
import com.example.customerquerysite.repositories.CustomerRepository;
import coreapi.query.GetAllCustomersQuery;
import coreapi.query.GetCustomerByIdQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CustomerQueryHandler {

    private CustomerRepository customerRepository;


    @QueryHandler
    public List<Customer> getCustomers(GetAllCustomersQuery query) {
        return customerRepository.findAll();
    }


    @QueryHandler
    public Customer getCustomerbyId(GetCustomerByIdQuery query) {
        return customerRepository.findById(query.getId()).orElseThrow(
                () -> new RuntimeException("Customer not found")
        );
    }


}
