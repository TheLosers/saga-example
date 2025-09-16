package com.example.saga.service;

import com.example.saga.event.PaymentCompletedEvent;
import com.example.saga.event.ShipmentScheduledEvent;
import com.example.saga.order.Order;
import com.example.saga.order.OrderStatus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ShippingService {
    private final ApplicationEventPublisher eventPublisher;

    public ShippingService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void onPaymentCompleted(PaymentCompletedEvent event) {
        Order order = event.order();
        order.setStatus(OrderStatus.SHIPPED);
        eventPublisher.publishEvent(new ShipmentScheduledEvent(order));
    }
}
