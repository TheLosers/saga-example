# saga-example

This project demonstrates an order processing workflow that uses the Saga **orchestration** pattern. The orchestrator
coordinates payment, shipping and point services and records both the forward path and the compensating actions.

## Workflow
1. Order is created.
2. Payment is processed.
3. Shipment is arranged.
4. Points are earned.
5. Order is completed. If any step fails the orchestrator triggers the compensating actions in reverse order and the
   order ends in the `CANCELLED` state.

## Running
Build and run using Maven:

```bash
mvn spring-boot:run
```

Create an order with:

```bash
curl -X POST http://localhost:8080/orders/{id}
```

The response contains both the final order status and a `history` array that documents how the orchestrator advanced the
workflow. Use the following order id suffixes to simulate failures and see the compensating logic:

* `fail-payment`
* `fail-shipping`
* `fail-points`

For example, executing `curl -X POST http://localhost:8080/orders/demo-fail-shipping` will trigger a shipping failure and
produce a cancelled order with payment refunded.
