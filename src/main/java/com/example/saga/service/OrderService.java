package com.example.saga.service;

import com.example.saga.order.Order;
import com.example.saga.order.OrderStatus;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final PaymentService paymentService;
    private final ShippingService shippingService;
    private final PointService pointService;

    public OrderService(PaymentService paymentService, ShippingService shippingService, PointService pointService) {
        this.paymentService = paymentService;
        this.shippingService = shippingService;
        this.pointService = pointService;
    }

    public Order placeOrder(String orderId) {
        Order order = new Order(orderId);
        paymentService.pay(order);
        shippingService.ship(order);
        pointService.earnPoints(order);
        order.setStatus(OrderStatus.COMPLETED);
        return order;
    }
}
