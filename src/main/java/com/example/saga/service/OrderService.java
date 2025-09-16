package com.example.saga.service;

import com.example.saga.orchestration.OrderSagaOrchestrator;
import com.example.saga.order.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderSagaOrchestrator orchestrator;

    public OrderService(OrderSagaOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    public Order placeOrder(String orderId) {
        return orchestrator.execute(orderId);
    }
}
