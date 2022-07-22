package com.example.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.system.entity.SchoolInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yt
 * @since 2022-05-24
 */
public interface SchoolInfoService extends IService<SchoolInfo> {

    Object insertSchoolInfo(SchoolInfo schoolInfo);

    Object getSchoolInfoList();

}
