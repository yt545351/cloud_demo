package com.yt.rabbitmqproducer.service.impl;

import com.yt.rabbitmqproducer.config.RabbitMQConfig;
import com.yt.rabbitmqproducer.service.RabbitMQService;
import com.yt.rabbitmqproducer.vo.MessageVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class RabbitMQServiceImpl implements RabbitMQService {
    //日期格式化
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public String sendMsg(MessageVO messageVO) {
        Map<String, Object> map = getMessage(messageVO.getMessage());
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.RABBITMQ_DEMO_DIRECT_EXCHANGE, RabbitMQConfig.RABBITMQ_DEMO_DIRECT_ROUTING, map);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @Override
    public Object sendMsgFanout(MessageVO messageVO) {
        Map<String, Object> map = getMessage(messageVO.getMessage());
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE_DEMO_NAME, "", map);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }

    @Override
    public Object sendMsgTopic(MessageVO messageVO) {
        Map<String, Object> map = getMessage(messageVO.getMessage());
        String routingKey = messageVO.getRoutingKey();
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_DEMO_NAME, routingKey, map);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    private Map<String, Object> getMessage(String msg) {
        String msgId = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
        String sendTime = sdf.format(new Date());
        Map<String, Object> map = new HashMap<>();
        map.put("msgId", msgId);
        map.put("sendTime", sendTime);
        map.put("msg", msg);
        return map;
    }
}
