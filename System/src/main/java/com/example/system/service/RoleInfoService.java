package com.example.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.system.entity.RoleInfo;
import com.example.system.vo.QueryRoleInfoVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yt
 * @since 2022-05-27
 */
public interface RoleInfoService extends IService<RoleInfo> {

    Object insertRole(RoleInfo roleInfo);

    Object getRoleList(QueryRoleInfoVO queryVO);

    Object deleteRole(RoleInfo roleInfo);

    Object updateRole(RoleInfo roleInfo);
}
