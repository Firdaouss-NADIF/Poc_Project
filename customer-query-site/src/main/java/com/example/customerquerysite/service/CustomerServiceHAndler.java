package com.example.customerquerysite.service;


import com.example.customerquerysite.entities.Customer;
import com.example.customerquerysite.repositories.CustomerRepository;
import coreapi.events.CustomerCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class CustomerServiceHAndler {

    private CustomerRepository customerRepository;


    @EventHandler
    public void on(CustomerCreatedEvent event) {
        log.info("*******************");
        log.info("CustomerCreatedEvent received");
        Customer customer = new Customer();
        customer.setId(event.getId());
        customer.setEmail(event.getEmail());
        customer.setName(event.getName());

        customerRepository.save(customer);
    }


}
