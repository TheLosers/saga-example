package com.example.saga.service;

import com.example.saga.order.Order;
import com.example.saga.order.OrderStatus;
import org.springframework.stereotype.Service;

@Service
public class PointService {
    public void earnPoints(Order order) {
        order.addHistory("Earning loyalty points");
        if (order.hasTag("fail-points")) {
            throw new IllegalStateException("Point service unavailable");
        }
        order.setStatus(OrderStatus.POINTS_EARNED);
        order.addHistory("Points granted");
    }

    public void revertPoints(Order order) {
        order.addHistory("Points reverted");
    }
}
