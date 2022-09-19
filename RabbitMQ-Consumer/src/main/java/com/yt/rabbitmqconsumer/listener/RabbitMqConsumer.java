package com.yt.rabbitmqconsumer.listener;


import com.yt.rabbitmqconsumer.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queuesToDeclare = @Queue(RabbitMQConfig.RABBITMQ_DEMO_TOPIC))
public class RabbitMqConsumer {
    @RabbitHandler
    private void process(Map map) {
        System.out.println("消费者从服务端消费消息：" + map.toString());
    }
}
