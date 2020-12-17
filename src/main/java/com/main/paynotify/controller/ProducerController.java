package com.main.paynotify.controller;

import com.main.paynotify.api.PayNotifyMessage;
import com.main.paynotify.api.PayNotifyRequest;
import com.main.paynotify.api.PayNotifyResponse;
import com.main.paynotify.service.PayNotifyPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProducerController {

    private static final Logger LOG = LoggerFactory.getLogger(ProducerController.class);

    @Autowired
    private PayNotifyPublisherService service;

    @PostMapping("/publish/paymentNotification")
    public ResponseEntity<PayNotifyResponse> publishPaymentNotification(
            @RequestBody PayNotifyRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(service.publishPayNoficationEvent(request));
    }


}
