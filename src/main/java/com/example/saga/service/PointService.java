package com.example.saga.service;

import com.example.saga.event.PointsEarnedEvent;
import com.example.saga.event.ShipmentScheduledEvent;
import com.example.saga.order.Order;
import com.example.saga.order.OrderStatus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class PointService {
    private final ApplicationEventPublisher eventPublisher;

    public PointService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void onShipmentScheduled(ShipmentScheduledEvent event) {
        Order order = event.order();
        order.setStatus(OrderStatus.POINTS_EARNED);
        eventPublisher.publishEvent(new PointsEarnedEvent(order));
    }
}
