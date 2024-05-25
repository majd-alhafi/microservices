package com.example.notification_service;

import com.example.notification_service.events.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}
	@KafkaListener (topics = "NotificationTopic")
	public void handleNotification(OrderPlacedEvent orderPlacedEvent){
		log.error("Received Notification for Order {}",orderPlacedEvent.getOrderNumber());
	}
}
