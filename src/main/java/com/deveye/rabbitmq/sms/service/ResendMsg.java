package com.deveye.rabbitmq.sms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deveye.rabbitmq.sms.entity.MsgLog;
import com.deveye.rabbitmq.sms.utils.MessageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class ResendMsg {

    @Autowired
    private MsgLogService msgLogService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 最大投递次数
    private static final int MAX_TRY_COUNT = 3;

    /**
     * 每30s拉取投递失败的消息, 重新投递
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void resend() {
        log.info("开始执行定时任务(重新投递消息)");

        QueryWrapper<MsgLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 2);

        List<MsgLog> msgLogs = msgLogService.list(queryWrapper);
        msgLogs.forEach(msgLog -> {
            String msgId = msgLog.getMsgId();
            if (msgLog.getTryCount() >= MAX_TRY_COUNT) {
                msgLog.setStatus(2);
                msgLog.setUpdateTime(LocalDateTime.now());
                msgLogService.updateById(msgLog);

                log.info("超过最大重试次数, 消息投递失败, msgId: {}", msgId);
            } else {
                msgLog.setTryCount(msgLog.getTryCount() + 1);
                msgLog.setUpdateTime(LocalDateTime.now());
                msgLogService.updateById(msgLog);
                CorrelationData correlationData = new CorrelationData(msgId);
                rabbitTemplate.convertAndSend(msgLog.getExchangeName(), msgLog.getRoutingKeyName(), MessageHelper.objToMsg(msgLog.getMail()), correlationData);// 重新投递

                log.info("第 " + (msgLog.getTryCount()) + " 次重新投递消息");
            }
        });

        log.info("定时任务执行结束(重新投递消息)");
    }

}
