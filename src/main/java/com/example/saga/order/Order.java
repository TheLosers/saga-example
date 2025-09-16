package com.example.saga.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private final String id;
    private OrderStatus status;
    private final List<String> history;

    public Order(String id) {
        this.id = id;
        this.status = OrderStatus.CREATED;
        this.history = new ArrayList<>();
        this.history.add("Order created");
    }

    public String getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<String> getHistory() {
        return Collections.unmodifiableList(history);
    }

    public void addHistory(String entry) {
        history.add(entry);
    }

    public boolean hasTag(String tag) {
        return id.contains(tag);
    }
}
