package com.deveye.rabbitmq.sms.utils;

import lombok.Data;

@Data
public class Mail {

    String msgId;

    String to;
    String title;
    String content;
}
