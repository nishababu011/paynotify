package com.main.paynotify.api;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayNotifyResponse {

    private String status;

    private String message;

}
