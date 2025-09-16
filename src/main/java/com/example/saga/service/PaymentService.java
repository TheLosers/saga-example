package com.example.saga.service;

import com.example.saga.order.Order;
import com.example.saga.order.OrderStatus;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public void pay(Order order) {
        order.addHistory("Processing payment");
        if (order.hasTag("fail-payment")) {
            throw new IllegalStateException("Payment was declined");
        }
        order.setStatus(OrderStatus.PAID);
        order.addHistory("Payment completed");
    }

    public void refund(Order order) {
        order.addHistory("Payment refunded");
    }
}
