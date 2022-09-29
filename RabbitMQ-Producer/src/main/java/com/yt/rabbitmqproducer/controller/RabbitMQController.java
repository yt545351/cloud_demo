package com.yt.rabbitmqproducer.controller;

import com.yt.rabbitmqproducer.service.RabbitMQService;
import com.yt.rabbitmqproducer.vo.MessageVO;
import com.yt.rabbitmqproducer.vo.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbit")
@Api(tags = "RabbitMQ")
public class RabbitMQController {
    @Autowired
    public RabbitMQService rabbitMQService;

    @ApiOperation(value = "发送消息")
    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
    public Object sendMsg(@RequestBody MessageVO messageVO) {
        return new ResultBody<>(rabbitMQService.sendMsg(messageVO));
    }

    @ApiOperation(value = "发送消息Fanout")
    @RequestMapping(value = "/sendMsgFanout", method = RequestMethod.POST)
    public Object sendMsgFanout(@RequestBody MessageVO messageVO) {
        return new ResultBody<>(rabbitMQService.sendMsgFanout(messageVO));
    }
    @ApiOperation(value = "发送消息Topic")
    @RequestMapping(value = "/sendMsgTopic", method = RequestMethod.POST)
    public Object sendMsgTopic(@RequestBody MessageVO messageVO) {
        return new ResultBody<>(rabbitMQService.sendMsgTopic(messageVO));
    }
}
