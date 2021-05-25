package com.deveye.rabbitmq.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deveye.rabbitmq.sms.entity.MsgLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MsgLogMapper extends BaseMapper<MsgLog> {

}
