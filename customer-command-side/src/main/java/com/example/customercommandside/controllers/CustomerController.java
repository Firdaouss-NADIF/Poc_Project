package com.example.customercommandside.controllers;

import coreapi.commands.CreateCustomerCommand;
import coreapi.dtos.CustomerRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@Slf4j
@RequestMapping("/customers/commands")
@CrossOrigin("*")
public class CustomerController {
    private CommandGateway commandGateway;
    private EventStore eventStore;

    public CustomerController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/create")
    public CompletableFuture<String> createCustomer(@RequestBody CustomerRequestDto request) {

        CompletableFuture<String> response = commandGateway.send(new CreateCustomerCommand(
                UUID.randomUUID().toString(),
                request.getName(),
                request.getEmail()
        ));
        return response;
    }

    @GetMapping("/eventStore/{customerId}")
    public Stream eventStore(@PathVariable String customerId) {
        return eventStore.readEvents(customerId).asStream();
    }

}
