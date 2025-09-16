package com.example.saga.event;

import com.example.saga.order.Order;

public record PointsEarnedEvent(Order order) {
}
