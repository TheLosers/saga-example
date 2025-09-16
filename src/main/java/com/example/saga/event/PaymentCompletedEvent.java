package com.example.saga.event;

import com.example.saga.order.Order;

public record PaymentCompletedEvent(Order order) {
}
