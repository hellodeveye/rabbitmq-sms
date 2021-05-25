package com.deveye.rabbitmq.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class MsgLogService extends ServiceImpl<MsgLogMapper, MsgLog> {


}
