package com.sanedge.product_service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.sanedge.product_service.service.ProductService;

@Component
public class OrderReceiver {

    private final ProductService productService;

    @Autowired
    public OrderReceiver(ProductService productService) {
        this.productService = productService;
    }

    @KafkaListener(topics = "${order.kafka.topic}", groupId = "my-group-id")
    public void receiveOrderMessage(String message) {
        String[] parts = message.split(":");
        if (parts.length == 2) {
            Long productId = Long.parseLong(parts[0]);
            int quantity = Integer.parseInt(parts[1]);
            productService.reduceProductQuantity(productId, quantity);
        }
    }
}
