package com.main.paynotify.service;

import com.main.paynotify.api.PayNotifyMessage;
import com.main.paynotify.api.PayNotifyRequest;
import com.main.paynotify.api.PayNotifyResponse;
import com.main.paynotify.producer.NotificationProducer;
import com.main.paynotify.util.ConstantsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayNotifyPublisherService {

    @Autowired
    private NotificationProducer notificationProducer;

    public PayNotifyResponse publishPayNoficationEvent(PayNotifyRequest request) {
        notificationProducer.sendMessage(request);
        return PayNotifyResponse.builder()
                .status(ConstantsUtil.SUCCESS_STATUS)
                .message(ConstantsUtil.SUCCESS_MESSAGE).build();
    }
}
