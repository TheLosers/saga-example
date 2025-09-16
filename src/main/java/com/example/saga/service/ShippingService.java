package com.example.saga.service;

import com.example.saga.order.Order;
import com.example.saga.order.OrderStatus;
import org.springframework.stereotype.Service;

@Service
public class ShippingService {
    public void ship(Order order) {
        // business logic for shipping
        order.setStatus(OrderStatus.SHIPPED);
    }
}
