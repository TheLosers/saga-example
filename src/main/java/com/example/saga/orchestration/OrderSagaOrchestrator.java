package com.example.saga.orchestration;

import com.example.saga.order.Order;
import com.example.saga.order.OrderStatus;
import com.example.saga.service.PaymentService;
import com.example.saga.service.PointService;
import com.example.saga.service.ShippingService;
import org.springframework.stereotype.Component;

@Component
public class OrderSagaOrchestrator {
    private final PaymentService paymentService;
    private final ShippingService shippingService;
    private final PointService pointService;

    public OrderSagaOrchestrator(PaymentService paymentService,
                                 ShippingService shippingService,
                                 PointService pointService) {
        this.paymentService = paymentService;
        this.shippingService = shippingService;
        this.pointService = pointService;
    }

    public Order execute(String orderId) {
        Order order = new Order(orderId);
        order.addHistory("Saga orchestration started");

        boolean paymentCompleted = false;
        boolean shipmentBooked = false;
        boolean pointsGranted = false;

        try {
            paymentService.pay(order);
            paymentCompleted = true;

            shippingService.ship(order);
            shipmentBooked = true;

            pointService.earnPoints(order);
            pointsGranted = true;

            order.setStatus(OrderStatus.COMPLETED);
            order.addHistory("Order completed by orchestrator");
        } catch (RuntimeException ex) {
            order.addHistory("Saga failed: " + ex.getMessage());
            compensate(order, paymentCompleted, shipmentBooked, pointsGranted);
        }

        return order;
    }

    private void compensate(Order order,
                             boolean paymentCompleted,
                             boolean shipmentBooked,
                             boolean pointsGranted) {
        if (pointsGranted) {
            try {
                pointService.revertPoints(order);
            } catch (RuntimeException ignored) {
                order.addHistory("Point compensation failed");
            }
        }

        if (shipmentBooked) {
            try {
                shippingService.cancelShipment(order);
            } catch (RuntimeException ignored) {
                order.addHistory("Shipping compensation failed");
            }
        }

        if (paymentCompleted) {
            try {
                paymentService.refund(order);
            } catch (RuntimeException ignored) {
                order.addHistory("Payment compensation failed");
            }
        }

        order.setStatus(OrderStatus.CANCELLED);
        order.addHistory("Order cancelled by orchestrator");
    }
}
