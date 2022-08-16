package com.example.system.controller;

import com.example.system.tool.ResultTools;
import com.example.system.utils.RedisUtil;
import com.example.system.vo.LoginVO;
import com.example.system.vo.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/redis")
@Api(tags = "Redis")
@Slf4j
public class RedisController {

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "新增缓存")
    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public Object set(@RequestBody LoginVO loginVO) {
        //账号
        String username = "123";
        //密码
        String password = "123456";
        //密码错误次数Key
        String loginFailedNumKey = loginVO.getUsername() + "FailedNum";
        //账号锁定Key
        String loginLockKey = loginVO.getUsername() + "LoginLock";
        //账号锁定Value
        Object loginLockValue = redisUtil.get(loginLockKey);
        //判断是否有该账号
        if (!username.equals(loginVO.getUsername())) {
            return new ResultBody<>(ResultTools.resultMap(false, "该账号尚未被注册,请注册账号"));
        }
        //判断是否账号锁定
        if (loginLockValue != null) {
            return new ResultBody<>(ResultTools.resultMap(false, "该账号已被锁定，请30秒后再试"));
        } else {
            if (redisUtil.get(loginFailedNumKey) != null && (int) redisUtil.get(loginFailedNumKey) == 3) {
                redisUtil.delete(loginFailedNumKey);
            }
        }
        //判断密码是否正确
        if (password.equals(loginVO.getPassword())) {
            //登陆成功后删除登陆失败次数
            redisUtil.delete(loginFailedNumKey);
            return new ResultBody<>(ResultTools.resultMap(true, "登陆成功"));
        } else {
            Object loginFailedNumValue = redisUtil.get(loginFailedNumKey);
            if (loginFailedNumValue == null) {
                redisUtil.set(loginFailedNumKey, 1);
                return new ResultBody<>(ResultTools.resultMap(false, "密码错误1次,登陆失败"));
            } else {
                int loginFailedNum = (int) loginFailedNumValue;
                loginFailedNum++;
                if (loginFailedNum == 3) {
                    //设置账号锁定超时时间
                    redisUtil.setTimeOut(loginLockKey, 1, 15, TimeUnit.SECONDS);
                    redisUtil.update(loginFailedNumKey, loginFailedNum);
                    return new ResultBody<>(ResultTools.resultMap(false, "密码错误" + loginFailedNum + "次,锁定账号"));
                } else {
                    redisUtil.update(loginFailedNumKey, loginFailedNum);
                    return new ResultBody<>(ResultTools.resultMap(false, "密码错误" + loginFailedNum + "次,登陆失败"));
                }
            }
        }
    }
}

