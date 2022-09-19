package com.yt.rabbitmqconsumer.config;

public class RabbitMQConfig {
    /**
     * rabbitmq队列主题名称
     */
    public static final String RABBITMQ_DEMO_TOPIC = "rabbitmqDemoTopic";
    /**
     * rabbitmq交换机名称
     */
    public static final String RABBITMQ_DEMO_DIRECT_EXCHANGE = "rabbitmqDemoDirectExchange";
    /**
     * rabbitmq交换机和队列绑定的匹配见
     */
    public static final String RABBITMQ_DEMO_DIRECT_ROUTING = "rabbitmqDemoDirectRouting";

    public static final String FANOUT_EXCHANGE_QUEUE_TOPIC_A = "fanout.A";
    public static final String FANOUT_EXCHANGE_QUEUE_TOPIC_B = "fanout.B";
    public static final String FANOUT_EXCHANGE_DEMO_NAME = "fanout.exchange.demo.name";
}
