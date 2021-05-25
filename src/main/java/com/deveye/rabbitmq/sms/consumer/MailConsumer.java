package com.deveye.rabbitmq.sms.consumer;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.deveye.rabbitmq.sms.RabbitConfig;
import com.deveye.rabbitmq.sms.entity.MsgLog;
import com.deveye.rabbitmq.sms.service.MsgLogService;
import com.deveye.rabbitmq.sms.utils.Mail;
import com.deveye.rabbitmq.sms.utils.MailUtil;
import com.deveye.rabbitmq.sms.utils.MessageHelper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Component
@Slf4j
public class MailConsumer {

    @Autowired
    private MsgLogService msgLogService;

    @Autowired
    private MailUtil mailUtil;

    @RabbitListener(queues = RabbitConfig.MAIL_QUEUE_NAME)
    public void consume(Message message, Channel channel) throws IOException {
        Mail mail = (Mail) MessageHelper.msgToObj(message, Mail.class);
        log.info("收到消息: {}", mail.toString());

        String msgId = mail.getMsgId();

        MsgLog msgLog = msgLogService.getById(msgId);
        if (null == msgLog || msgLog.getStatus() == 3) {// 消费幂等性
            log.info("重复消费, msgId: {}", msgId);
            return;
        }

        MessageProperties properties = message.getMessageProperties();
        long tag = properties.getDeliveryTag();

        boolean success = mailUtil.send(mail);
        if (success) {
            msgLog.setStatus(3);
            msgLog.setUpdateTime(LocalDateTime.now());
            msgLogService.updateById(msgLog);
            channel.basicAck(tag, false);// 消费确认
        } else {
            channel.basicNack(tag, false, true);
        }
    }


}
