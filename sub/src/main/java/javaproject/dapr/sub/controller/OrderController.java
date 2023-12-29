package javaproject.dapr.sub.controller;

import io.dapr.Topic;
import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import io.dapr.client.domain.CloudEvent;
import io.dapr.client.domain.State;
import javaproject.dapr.sub.model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;


@RestController
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);


    @GetMapping(value = "/status")
    @ResponseStatus(HttpStatus.OK)
    public String helloWorld(){
        return "hello world";
    }

    @Topic(name = "orders", pubsubName = "kafka-commonpubsub")
    @PostMapping(path = "/orders", consumes = MediaType.ALL_VALUE)
    public Mono<ResponseEntity> getCheckout(@RequestBody(required = false) CloudEvent<Order> cloudEvent) {
        return Mono.fromSupplier(() -> {
            try {
                logger.info("Subscriber received: " + cloudEvent.getData().getOrderId());
                saveStateToBasketStore("basket-store", cloudEvent.getData().getOrderId(), cloudEvent.getData());

                Order orderFromRedis = getStateStoreOrder("basket-store", cloudEvent.getData().getOrderId());
                System.out.println("HERE IS THE RETRIEVED THING FROM THE STORE:" + orderFromRedis.toString());
                return ResponseEntity.ok("SUCCESS");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void saveStateToBasketStore(String stateStoreName, int key, Object object) throws Exception {
        try (DaprClient client = (new DaprClientBuilder()).build()) {
            // Save state
            client.saveState(stateStoreName, String.valueOf(key), object).block();
        }
    }

    private Order getStateStoreOrder(String stateStoreName, int key) throws Exception {
        try (DaprClient client = (new DaprClientBuilder()).build()) {
            // Get state
            State<Order> retrievedMessage = client.getState(stateStoreName, String.valueOf(key), Order.class).block();

            if(retrievedMessage == null)
                return null;

            return retrievedMessage.getValue();
        }
    }



}
