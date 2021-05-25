package com.deveye.rabbitmq.sms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.deveye.rabbitmq.sms.utils.Mail;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@TableName("msg_log")
@NoArgsConstructor
public class MsgLog {


    @TableId(value = "msg_id", type = IdType.NONE)
    private String msgId;
    private String msg;


    @TableField("exchange")
    private String exchangeName;

    @TableField("routing_key")
    private String routingKeyName;

    private int status;

    @TableField("try_count")
    private int tryCount;


    @TableField("next_try_time")
    private LocalDateTime nextTryTime;


    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    private transient Mail mail;


    public MsgLog(String msgId, Mail mail, String mailExchangeName, String mailRoutingKeyName) {
        this.msgId = msgId;
        this.mail = mail;
        this.exchangeName = mailExchangeName;
        this.routingKeyName = mailRoutingKeyName;
        this.createTime = LocalDateTime.now();
        this.msg = mail.getContent();
    }
}
