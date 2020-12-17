package com.main.paynotify.api;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayNotifyMessage implements Serializable {

    private String customerId;

    private String subscriptionId;

    private String description;

    private String transactionDate;

    private String transactionType;

    private BigDecimal transactionAmount;

    @Override
    public String toString() {
        return "CustomerId : " + customerId + ", SubcriptionId: " + subscriptionId
                + ", Description: " + description + ", transactionDate: " + transactionDate
                + ", TransactionType: " + transactionType;
    }

}
