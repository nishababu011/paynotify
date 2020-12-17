package com.main.paynotify.producer;

import com.main.paynotify.api.PayNotifyMessage;
import com.main.paynotify.api.PayNotifyRequest;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationProducer {
    @Value(value = "${kafka.inputtopic}")
    private String inputTopic;

    @Autowired
    private KafkaTemplate<String, PayNotifyMessage> kafkaTemplate;

    public void sendMessage(PayNotifyRequest payNotifyRequest) {
        kafkaTemplate.send(new ProducerRecord<>(
                inputTopic, payNotifyRequest.getPaymentId(), getPayNotifyMessage(payNotifyRequest)));
    }

    private PayNotifyMessage getPayNotifyMessage(PayNotifyRequest payNotifyRequest) {
        return PayNotifyMessage.builder()
                .customerId(payNotifyRequest.getCustomerId())
                .description(payNotifyRequest.getDescription())
                .subscriptionId(payNotifyRequest.getSubscriptionId())
                .transactionAmount(payNotifyRequest.getTransactionAmount())
                .transactionDate(payNotifyRequest.getTransactionDate())
                .transactionType(payNotifyRequest.getTransactionType())
                .build();
    }
}
