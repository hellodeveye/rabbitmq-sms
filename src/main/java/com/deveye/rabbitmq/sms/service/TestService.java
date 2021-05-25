package com.deveye.rabbitmq.sms.service;

import com.deveye.rabbitmq.sms.RabbitConfig;
import com.deveye.rabbitmq.sms.entity.MsgLog;
import com.deveye.rabbitmq.sms.mapper.MsgLogMapper;
import com.deveye.rabbitmq.sms.utils.Mail;
import com.deveye.rabbitmq.sms.utils.MessageHelper;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TestService {


    @Autowired
    private MsgLogMapper msgLogMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Map<String, Object> send(Mail mail) {

        Map<String, Object> map = new HashMap<>();
        String msgId = UUID.randomUUID().toString().replace("-", "");
        mail.setMsgId(msgId);

        MsgLog msgLog = new MsgLog(msgId, mail, RabbitConfig.MAIL_EXCHANGE_NAME, RabbitConfig.MAIL_ROUTING_KEY_NAME);
        msgLogMapper.insert(msgLog);// 消息入库

        CorrelationData correlationData = new CorrelationData(msgId);
        rabbitTemplate.convertAndSend(RabbitConfig.MAIL_EXCHANGE_NAME, RabbitConfig.MAIL_ROUTING_KEY_NAME, MessageHelper.objToMsg(mail), correlationData);// 发送消息

        map.put("msg", "发送成功");

        return map;
    }

}