package com.yt.rabbitmqproducer.service;

import com.yt.rabbitmqproducer.vo.MessageVO;

public interface RabbitMQService {
    String sendMsg(MessageVO messageVO);

    Object sendMsgFanout(MessageVO messageVO);

    Object sendMsgTopic(MessageVO messageVO);
}
