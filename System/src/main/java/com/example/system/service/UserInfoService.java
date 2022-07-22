package com.example.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.system.entity.UserInfo;
import com.example.system.vo.LoginVO;
import com.example.system.vo.QueryUserInfoVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yt
 * @since 2022-05-18
 */
public interface UserInfoService extends IService<UserInfo> {

    Object login(LoginVO loginVo);

    Object insertUser(UserInfo userInfo);

    Object getUserInfoList(QueryUserInfoVO queryVO);

    Object getUserMenu(QueryUserInfoVO queryVO);

    Object updateUser(UserInfo userInfo);

    Object deleteUser(UserInfo userInfo);
}
