package com.example.sb1031.orders;

import org.springframework.context.ApplicationEvent;

public class OrdersEvent extends ApplicationEvent {

    private Order order;

    private Long orderId;

    public OrdersEvent(Object source,Order order) {
        super(source);
        this.order = order;
    }

    public OrdersEvent(Object source, Long orderId) {
        super(source);
        this.orderId = orderId;
    }

    public Order getOrder() {
        return order;
    }

    public Long getOrderId() {
        return orderId;
    }

}
