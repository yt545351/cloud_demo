package com.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.system.entity.SchoolInfo;
import com.example.system.mapper.SchoolInfoMapper;
import com.example.system.service.SchoolInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yt
 * @since 2022-05-24
 */
@Service
public class SchoolInfoServiceImpl extends ServiceImpl<SchoolInfoMapper, SchoolInfo> implements SchoolInfoService {

    @Autowired
    private SchoolInfoMapper schoolInfoMapper;

    @Override
    public Object insertSchoolInfo(SchoolInfo schoolInfo) {
        String school_id = schoolInfo.getSchool_id();
        String school_name = schoolInfo.getSchool_name();
        LocalDateTime now = LocalDateTime.now();
        for (int i = 2022; i >= 2000; i--) {
            SchoolInfo s1 = new SchoolInfo();
            s1.setSchool_id(school_id);
            s1.setSchool_name(school_name);
            s1.setYear_no(i);
            s1.setTeacher_num((long) (new Random().nextInt(500) + 2000));
            s1.setStudent_num((long) (new Random().nextInt(50) + 200));
            s1.setCreate_time(now);
            s1.setUpdate_time(now);
            int insert = schoolInfoMapper.insert(s1);
        }
        return true;
    }

    @Override
    public Object getSchoolInfoList() {
        List<Map<String, Object>> dataList = new ArrayList<>();
        //获取学校名称map
        Map<String, Object> schoolNameMap = new HashMap<>();
        List<SchoolInfo> schoolInfoList = schoolInfoMapper.selectList(new QueryWrapper<SchoolInfo>().select("distinct school_id,school_name"));
        for (SchoolInfo schoolInfo : schoolInfoList) {
            String school_id = schoolInfo.getSchool_id();
            String school_name = schoolInfo.getSchool_name();
            schoolNameMap.put(school_id, school_name);
        }

        List<String> schoolIdList = schoolInfoList.stream().map(SchoolInfo::getSchool_id).collect(Collectors.toList());
        for (String schoolId : schoolIdList) {
            List<SchoolInfo> list = schoolInfoMapper.selectList(new QueryWrapper<SchoolInfo>()
                    .eq("school_id", schoolId)
                    .orderByDesc("year_no")
                    .last("limit 7"));
            Map<String, Object> map = new HashMap<>();
            map.put("name", schoolNameMap.get(schoolId));
            List<Long> teacherList = list.stream().map(SchoolInfo::getTeacher_num).collect(Collectors.toList());
            List<Long> studentList = list.stream().map(SchoolInfo::getStudent_num).collect(Collectors.toList());
            Collections.reverse(teacherList);
            Collections.reverse(studentList);
            map.put("teacherList", teacherList);
            map.put("studentList", studentList);
            dataList.add(map);
        }
        //日期集合
        List<Integer> dateList = schoolInfoMapper.selectList(new QueryWrapper<SchoolInfo>().select("distinct year_no")
                .orderByDesc("year_no")
                .last("limit 7")).stream().map(SchoolInfo::getYear_no).collect(Collectors.toList());
        Collections.reverse(dateList);
        Map<String, Object> result = new HashMap<>();
        result.put("data", dataList);
        result.put("date", dateList);
        return result;
    }
}
