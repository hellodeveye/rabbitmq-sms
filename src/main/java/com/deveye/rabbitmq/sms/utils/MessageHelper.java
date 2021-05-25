package com.deveye.rabbitmq.sms.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.Message;

public class MessageHelper {


    public static Object objToMsg(Mail mail) {

        return JSON.toJSON(mail);
    }

    public static Object msgToObj(Message message, Class<?> clazz) {

        return JSON.parseObject(new String(message.getBody()), clazz);
    }
}
