package com.example.sb1031.event;

import com.example.sb1031.orders.OrdersEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void doStuffAndPublishAnEvent(final String message){
        System.out.println("Publishing custom event ");
        CustomEvent customSpringEvent = new CustomEvent(this, message);
        applicationEventPublisher.publishEvent(customSpringEvent);
    }

    public void publishOrderEvent(final OrdersEvent orderEvent) {
        System.out.println("Publishing custom OrdersEvent");
        applicationEventPublisher.publishEvent(orderEvent);
    }
}
