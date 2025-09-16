package com.example.saga.service;

import com.example.saga.order.Order;
import com.example.saga.order.OrderStatus;
import org.springframework.stereotype.Service;

@Service
public class ShippingService {
    public void ship(Order order) {
        order.addHistory("Arranging shipment");
        if (order.hasTag("fail-shipping")) {
            throw new IllegalStateException("Shipping provider error");
        }
        order.setStatus(OrderStatus.SHIPPED);
        order.addHistory("Shipment booked");
    }

    public void cancelShipment(Order order) {
        order.addHistory("Shipment cancelled");
    }
}
