package com.kafka.presentation.producer;

import lombok.*;

@EqualsAndHashCode
@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class NotificationParam {

    private String userId;
    private String message;
    private String title;
}
