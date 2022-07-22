package com.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.system.entity.PageBean;
import com.example.system.entity.RoleInfo;
import com.example.system.mapper.RoleInfoMapper;
import com.example.system.service.RoleInfoService;
import com.example.system.utils.StringUtils;
import com.example.system.vo.QueryRoleInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.system.utils.ResultUtils.resultMap;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yt
 * @since 2022-05-27
 */
@Service
@Slf4j
public class RoleInfoServiceImpl extends ServiceImpl<RoleInfoMapper, RoleInfo> implements RoleInfoService {

    @Autowired
    private RoleInfoMapper roleInfoMapper;

    @Override
    public Object insertRole(RoleInfo roleInfo) {
        RoleInfo role = roleInfoMapper.selectOne(new QueryWrapper<RoleInfo>().orderByDesc("create_time").last("limit 1"));
        if (role == null) {
            roleInfo.setRole_id("r001");
        } else {
            String role_id = role.getRole_id();
            Integer i = Integer.valueOf(role_id.split("r00")[1]);
            i++;
            role_id = "r00" + i;
            roleInfo.setRole_id(role_id);
            log.info("role:{}", roleInfo);
        }
        LocalDateTime now = LocalDateTime.now();
        roleInfo.setCreate_time(now);
        roleInfo.setUpdate_time(now);
        int i = roleInfoMapper.insert(roleInfo);
        return i > 0 ? resultMap(true, "新增成功") : resultMap(false, "新增失败");
    }

    @Override
    public Object getRoleList(QueryRoleInfoVO queryVO) {
        List<RoleInfo> list = roleInfoMapper.selectList(new QueryWrapper<RoleInfo>().ge(StringUtils.isNotEmpty(queryVO.getStart_time()), "create_time", queryVO.getStart_time()).le(StringUtils.isNotEmpty(queryVO.getEnd_time()), "create_time", queryVO.getEnd_time()).like(StringUtils.isNotEmpty(queryVO.getRole_name()), "role_name", queryVO.getRole_name()).orderByDesc("create_time"));
        if (queryVO.getPageNum() == null || queryVO.getPageNum() == 0) {
            return list;
        } else {
            PageBean<RoleInfo> pageBean = new PageBean<>(list, queryVO.getPageNum(), queryVO.getPageSize());
            return pageBean;
        }
    }

    @Override
    public Object deleteRole(RoleInfo roleInfo) {
        int i = roleInfoMapper.deleteById(roleInfo);
        return i > 0 ? resultMap(true, "删除成功") : resultMap(false, "删除失败");
    }

    @Override
    public Object updateRole(RoleInfo roleInfo) {
        RoleInfo one = roleInfoMapper.selectById(roleInfo);
        roleInfo.setCreate_time(one.getCreate_time());
        roleInfo.setUpdate_time(LocalDateTime.now());
        int i = roleInfoMapper.updateById(roleInfo);
        return i > 0 ? resultMap(true, "更新成功") : resultMap(false, "更新失败");
    }
}
