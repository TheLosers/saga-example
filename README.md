# saga-example

This project demonstrates an order processing workflow using the **choreography Saga pattern** implemented with Spring's application events. Each service reacts to domain events and publishes a new event to trigger the next step, allowing the workflow to progress without a central orchestrator.

## Workflow
1. `OrderService` publishes an `OrderCreatedEvent` when an order is placed.
2. `PaymentService` marks the order as paid and emits a `PaymentCompletedEvent`.
3. `ShippingService` schedules shipment and raises a `ShipmentScheduledEvent`.
4. `PointService` assigns reward points and publishes a `PointsEarnedEvent`.
5. `OrderService` listens for the `PointsEarnedEvent` and completes the order.

## Running
Build and run using Maven:

```bash
mvn spring-boot:run
```

Create an order with:

```bash
curl -X POST http://localhost:8080/orders/{id}
```

This will return the final order state.
