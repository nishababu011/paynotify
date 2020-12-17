package com.main.paynotify.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PayNotifyRequest implements Serializable {

    private String paymentId;

    private String customerId;

    private String subscriptionId;

    private String description;

    private String transactionDate;

    private String transactionType;

    private BigDecimal transactionAmount;
}
