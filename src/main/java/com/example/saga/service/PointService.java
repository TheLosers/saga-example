package com.example.saga.service;

import com.example.saga.order.Order;
import com.example.saga.order.OrderStatus;
import org.springframework.stereotype.Service;

@Service
public class PointService {
    public void earnPoints(Order order) {
        // business logic for earning points
        order.setStatus(OrderStatus.POINTS_EARNED);
    }
}
