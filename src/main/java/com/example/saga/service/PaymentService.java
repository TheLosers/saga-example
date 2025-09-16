package com.example.saga.service;

import com.example.saga.order.Order;
import com.example.saga.order.OrderStatus;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public void pay(Order order) {
        // business logic for payment processing
        order.setStatus(OrderStatus.PAID);
    }
}
