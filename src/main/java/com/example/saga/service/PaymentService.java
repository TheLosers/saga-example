package com.example.saga.service;

import com.example.saga.event.OrderCreatedEvent;
import com.example.saga.event.PaymentCompletedEvent;
import com.example.saga.order.Order;
import com.example.saga.order.OrderStatus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final ApplicationEventPublisher eventPublisher;

    public PaymentService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void onOrderCreated(OrderCreatedEvent event) {
        Order order = event.order();
        order.setStatus(OrderStatus.PAID);
        eventPublisher.publishEvent(new PaymentCompletedEvent(order));
    }
}
