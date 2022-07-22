package com.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.system.entity.SchoolInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yt
 * @since 2022-05-24
 */
@Repository
@Mapper
public interface SchoolInfoMapper extends BaseMapper<SchoolInfo> {

}
