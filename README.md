# saga-example

This project demonstrates an order processing workflow **without** the Saga pattern. It serves as a baseline for later examples that apply the orchestration and choreography Saga patterns.

## Workflow
1. Order is created.
2. Payment is processed.
3. Shipment is arranged.
4. Points are earned.
5. Order is completed.

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
