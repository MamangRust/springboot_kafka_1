package com.sanedge.order_service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderSender {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${order.kafka.topic}")
    private String topic;

    @Autowired
    public OrderSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderMessage(Long productId, int quantity) {
        String message = productId + ":" + quantity;
        kafkaTemplate.send(topic, message);
        System.out.println("Order message sent to Kafka: " + message);
    }
}
