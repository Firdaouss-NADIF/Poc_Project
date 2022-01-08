package com.example.customercommandside.aggregates;


import coreapi.commands.CreateCustomerCommand;
import coreapi.events.CustomerCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class CustomerAggregate {
    @AggregateIdentifier
    public String id;
    public String name;
    public String email;

    public CustomerAggregate() {

    }

    @CommandHandler
    public CustomerAggregate(CreateCustomerCommand command) {
        log.info("CreateCustomerCommand received");

        AggregateLifecycle.apply(new CustomerCreatedEvent(
                command.getId(),
                command.getName(),
                command.getEmail()
        ));
    }

    @EventSourcingHandler
    public void on(CustomerCreatedEvent event) {
        log.info("CustomerCreatedEvent occured");
        this.id = event.getId();
        this.name = event.getName();
        this.email = event.getEmail();
    }

}
