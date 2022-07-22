package com.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.system.entity.UserInfo;
import com.example.system.vo.QueryUserInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yt
 * @since 2022-05-18
 */
@Mapper
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    List<Map<String, Object>> queryUserList(@Param("queryVO") QueryUserInfoVO queryVO);
}
