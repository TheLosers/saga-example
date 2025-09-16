package com.example.saga.orchestration;

import com.example.saga.order.Order;
import com.example.saga.order.OrderStatus;
import com.example.saga.service.PaymentService;
import com.example.saga.service.PointService;
import com.example.saga.service.ShippingService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderSagaOrchestratorTest {

    private final OrderSagaOrchestrator orchestrator = new OrderSagaOrchestrator(
            new PaymentService(),
            new ShippingService(),
            new PointService()
    );

    @Test
    void completesSagaWhenNoFailuresOccur() {
        Order order = orchestrator.execute("order-123");

        assertThat(order.getStatus()).isEqualTo(OrderStatus.COMPLETED);
        assertThat(order.getHistory())
                .containsSequence(
                        "Order created",
                        "Saga orchestration started",
                        "Processing payment",
                        "Payment completed",
                        "Arranging shipment",
                        "Shipment booked",
                        "Earning loyalty points",
                        "Points granted",
                        "Order completed by orchestrator"
                );
    }

    @Test
    void compensatesWhenShippingFails() {
        Order order = orchestrator.execute("order-42-fail-shipping");

        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCELLED);
        assertThat(order.getHistory())
                .contains(
                        "Saga failed: Shipping provider error",
                        "Payment refunded",
                        "Order cancelled by orchestrator"
                )
                .doesNotContain("Points granted");
    }

    @Test
    void cancelsShipmentAndRefundsPaymentWhenPointsFail() {
        Order order = orchestrator.execute("order-99-fail-points");

        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCELLED);
        assertThat(order.getHistory())
                .contains(
                        "Shipment booked",
                        "Saga failed: Point service unavailable",
                        "Shipment cancelled",
                        "Payment refunded",
                        "Order cancelled by orchestrator"
                )
                .doesNotContain("Points granted");
    }
}
