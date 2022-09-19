package com.yt.rabbitmqconsumer.listener;

import com.yt.rabbitmqconsumer.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queuesToDeclare = @Queue(RabbitMQConfig.FANOUT_EXCHANGE_QUEUE_TOPIC_A))
public class FanoutExchangeConsumerA {
    @RabbitHandler
    private void process(Map<String, Object> map) {
        System.out.println("队列A收到消息:" + map.toString());
    }
}
