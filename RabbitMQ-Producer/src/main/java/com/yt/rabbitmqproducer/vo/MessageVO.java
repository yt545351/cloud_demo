package com.yt.rabbitmqproducer.vo;

import lombok.Data;

@Data
public class MessageVO {
    private String message;
    private String routingKey;
}
