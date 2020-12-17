package com.main.paynotify.consumer;

import com.main.paynotify.api.PayNotifyMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationConsumer {

    @KafkaListener(topics = "${kafka.inputtopic}")
    public void receiveMessage(@Payload PayNotifyMessage message, @Headers MessageHeaders headers) {
        log.info("Received Payment Notification -> {}", message);
        headers.keySet().forEach(k -> log.info("Header {} -> {}", k, headers.get(k)));
    }

}
