package com.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.system.entity.PageBean;
import com.example.system.entity.RoleInfo;
import com.example.system.entity.UserInfo;
import com.example.system.mapper.RoleInfoMapper;
import com.example.system.mapper.UserInfoMapper;
import com.example.system.service.UserInfoService;
import com.example.system.utils.EncryptAesUtil;
import com.example.system.utils.RedisUtil;
import com.example.system.utils.StringUtils;
import com.example.system.vo.LoginVO;
import com.example.system.vo.QueryUserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.system.utils.ResultUtils.resultMap;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yt
 * @since 2022-05-18
 */
@Service
@Slf4j
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RoleInfoMapper roleInfoMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Object login(LoginVO loginVo) {
        //获取数据库中登陆账号信息
        UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>()
                .eq("username", loginVo.getUsername()));
        //判断该账号是否存在
        if (userInfo == null) {
            return resultMap(false, "该账号尚未被注册,请注册账号");
        }

        String loginPassword = EncryptAesUtil.aesDecrypt(loginVo.getPassword());
        String sqlPassword = EncryptAesUtil.aesDecrypt(userInfo.getPassword());

        //密码错误次数Key
        String loginFailedNumKey = loginVo.getUsername() + "FailedNum";
        //账号锁定Key
        String loginLockKey = loginVo.getUsername() + "LoginLock";
        //账号锁定Value
        Object loginLockValue = redisUtil.get(loginLockKey);

        //判断是否账号锁定
        if (loginLockValue != null) {
            return resultMap(false, "该账号已被锁定，请30秒后再试");
        } else {
            if (redisUtil.get(loginFailedNumKey) != null && (int) redisUtil.get(loginFailedNumKey) == 3) {
                redisUtil.delete(loginFailedNumKey);
            }
        }
        //判断密码是否正确
        if (sqlPassword.equals(loginPassword)) {
            //登陆成功后删除登陆失败次数
            redisUtil.delete(loginFailedNumKey);
            return resultMap(true, "登陆成功");
        } else {
            Object loginFailedNumValue = redisUtil.get(loginFailedNumKey);
            if (loginFailedNumValue == null) {
                redisUtil.set(loginFailedNumKey, 1);
                return resultMap(false, "密码错误1次,登陆失败");
            } else {
                int loginFailedNum = (int) loginFailedNumValue;
                loginFailedNum++;
                //设置密码失败3次锁定账号
                if (loginFailedNum == 3) {
                    //设置账号锁定超时时间
                    redisUtil.setTimeOut(loginLockKey, 1, 15, TimeUnit.SECONDS);
                    redisUtil.update(loginFailedNumKey, loginFailedNum);
                    return resultMap(false, "密码错误" + loginFailedNum + "次,锁定账号");
                } else {
                    redisUtil.update(loginFailedNumKey, loginFailedNum);
                    return resultMap(false, "密码错误" + loginFailedNum + "次,登陆失败");
                }
            }
        }
    }

    @Override
    public Object insertUser(UserInfo userInfo) {
        //判断是否有该账户
        UserInfo user = userInfoMapper.selectOne(new QueryWrapper<UserInfo>()
                .eq("username", userInfo.getUsername()));
        if (user != null) {
            return resultMap(false, "已有该账户");
        }
        //AES加密
        String password = EncryptAesUtil.aesEncrypt(userInfo.getPassword());
        userInfo.setPassword(password);

        LocalDateTime now = LocalDateTime.now();
        userInfo.setCreate_time(now);
        userInfo.setUpdate_time(now);
        int i = userInfoMapper.insert(userInfo);
        return i > 0 ? resultMap(true, "新增成功") : resultMap(false, "新增失败");
    }

    @Override
    public Object getUserInfoList(QueryUserInfoVO queryVO) {
        List<UserInfo> list = userInfoMapper.selectList(new QueryWrapper<UserInfo>()
                .select("id,create_time,role_id,tell,update_time,username")
                .eq(StringUtils.isNotEmpty(queryVO.getUsername()), "username", queryVO.getUsername())
                .eq(StringUtils.isNotEmpty(queryVO.getRole_id()), "role_id", queryVO.getRole_id())
                .orderByDesc("create_time"));
        PageBean<UserInfo> pageBean = new PageBean<>(list, queryVO.getPageNum(), queryVO.getPageSize());
//        List<Map<String, Object>> maps = userInfoMapper.queryUserList(queryVO);
        return pageBean;
    }

    @Override
    public Object getUserMenu(QueryUserInfoVO queryVO) {
        UserInfo user = userInfoMapper.selectOne(new QueryWrapper<UserInfo>()
                .eq("username", queryVO.getUsername()));
        RoleInfo roleInfo = roleInfoMapper.selectOne(new QueryWrapper<RoleInfo>()
                .select("role_name,menu_json")
                .eq("role_id", user.getRole_id()));
        return roleInfo;
    }

    @Override
    public Object updateUser(UserInfo userInfo) {
        //判断密码是否相同
        UserInfo user = userInfoMapper.selectById(userInfo.getId());
        String sqlPassword = EncryptAesUtil.aesDecrypt(user.getPassword());
        if (userInfo.getPassword().equals(sqlPassword)) {
            userInfo.setPassword(user.getPassword());
        } else {
            userInfo.setPassword(EncryptAesUtil.aesEncrypt(userInfo.getPassword()));
        }
        int i = userInfoMapper.updateById(userInfo);
        return i > 0 ? resultMap(true, "更新成功") : resultMap(false, "更新失败");
    }

    @Override
    public Object deleteUser(UserInfo userInfo) {
        int i = userInfoMapper.deleteById(userInfo);
        return i > 0 ? resultMap(true, "删除成功") : resultMap(false, "删除失败");
    }
}
