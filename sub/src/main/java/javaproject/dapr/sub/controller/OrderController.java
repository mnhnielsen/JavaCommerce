package javaproject.dapr.sub.controller;

import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;
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
        System.out.println("GOT ORDERS");
        return Mono.fromSupplier(() -> {
            try {
                logger.info("Subscriber received: " + cloudEvent.getData().getOrderId());
                return ResponseEntity.ok("SUCCESS");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }



}
