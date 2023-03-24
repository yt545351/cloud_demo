package com.example.system.controller;

import com.example.system.entity.Proclamation;
import com.example.system.service.TestService;
import com.example.system.utils.SystemInfoUtil;
import com.example.system.vo.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Api("测试接口")
@RestController
@Slf4j
@RefreshScope
public class TestController {

    @Autowired
    private TestService testService;

    @ApiOperation(value = "定时建表任务")
    @RequestMapping(value = "/createTable", method = RequestMethod.POST)
    public Object createTable() {
        return new ResultBody<>(testService.createTable());
    }

    @ApiOperation(value = "获取Ip地址")
    @RequestMapping(value = "/getIp", method = RequestMethod.POST)
    public Object getIp(HttpServletRequest request) {
        System.out.println(getIpAddr(request));
        return new ResultBody<>();
    }

    public String getIpAddr(HttpServletRequest request) {
        //目前则是网关ip
        String ip = request.getHeader("X-Real-IP");
        if (ip != null && !"".equals(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !"".equals(ip) && !"unknown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(',');
            if (index != -1) {
                //只获取第一个值
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            //取不到真实ip则返回空，不能返回内网地址。
            return "";
        }
    }

}
