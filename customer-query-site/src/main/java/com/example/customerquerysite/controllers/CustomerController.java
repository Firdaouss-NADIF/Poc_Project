package com.example.customerquerysite.controllers;

import com.example.customerquerysite.entities.Customer;
import coreapi.query.GetAllCustomersQuery;
import coreapi.query.GetCustomerByIdQuery;
import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/customers/query/")
@AllArgsConstructor
@CrossOrigin("*")
public class CustomerController {

    private QueryGateway queryGateway;


    @GetMapping("/all")
    public CompletableFuture<List<Customer>> getCustomers() {
        CompletableFuture<List<Customer>> response = queryGateway.query(new GetAllCustomersQuery(),
                ResponseTypes.multipleInstancesOf(Customer.class));
        return response;
    }

    @GetMapping("/{id}")
    public CompletableFuture<Customer> getCustomerById(@PathVariable String id) {

        CompletableFuture<Customer> response = queryGateway.query(new GetCustomerByIdQuery(id), ResponseTypes.instanceOf(Customer.class));

        return response;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception) {
        ResponseEntity<String> responseEntity =
                new ResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }

}
