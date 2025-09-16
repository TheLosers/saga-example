package com.example.saga.service;

import com.example.saga.event.OrderCreatedEvent;
import com.example.saga.event.PointsEarnedEvent;
import com.example.saga.order.Order;
import com.example.saga.order.OrderStatus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final ApplicationEventPublisher eventPublisher;

    public OrderService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public Order placeOrder(String orderId) {
        Order order = new Order(orderId);
        eventPublisher.publishEvent(new OrderCreatedEvent(order));
        return order;
    }

    @EventListener
    public void onPointsEarned(PointsEarnedEvent event) {
        Order order = event.order();
        order.setStatus(OrderStatus.COMPLETED);
    }
}
