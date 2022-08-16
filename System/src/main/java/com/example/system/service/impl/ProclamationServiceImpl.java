package com.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.system.entity.PageBean;
import com.example.system.entity.Proclamation;
import com.example.system.mapper.ProclamationMapper;
import com.example.system.service.ProclamationService;
import com.example.system.tool.FileTools;
import com.example.system.tool.ObjectTools;
import com.example.system.tool.ResultTools;
import com.example.system.tool.StringTools;
import com.example.system.vo.QueryProclamationVO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yt
 * @since 2022-05-20
 */
@Service
@Slf4j
public class ProclamationServiceImpl extends ServiceImpl<ProclamationMapper, Proclamation> implements ProclamationService {

    @Autowired
    private ProclamationMapper proclamationMapper;

    @Override
    public Object insertProclamation(Proclamation proclamation) {
        LocalDateTime now = LocalDateTime.now();
        proclamation.setCreate_time(now);
        proclamation.setUpdate_time(now);
        int i = proclamationMapper.insert(proclamation);
        return i > 0 ? ResultTools.resultMap(true, "新增成功") : ResultTools.resultMap(false, "新增失败");
    }

    @Override
    public Object getProclamationList(QueryProclamationVO queryVO) {
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (StringTools.isNotEmpty(queryVO.getStartTime())) {
            startTime = LocalDateTime.parse(queryVO.getStartTime(), dtf);
        }
        if (StringTools.isNotEmpty(queryVO.getEndTime())) {
            endTime = LocalDateTime.parse(queryVO.getEndTime(), dtf);
        }
        QueryWrapper<Proclamation> queryWrapper = new QueryWrapper<Proclamation>().ge(startTime != null, "create_time", startTime).le(endTime != null, "create_time", endTime).like(StringTools.isNotEmpty(queryVO.getTitle()), "title", queryVO.getTitle()).orderByDesc("create_time");

        List<Proclamation> list = proclamationMapper.selectList(queryWrapper);
        PageBean<Proclamation> page = new PageBean<>(list, queryVO.getPageNum(), queryVO.getPageSize());
        return page;
    }

    @Override
    public Object updateProclamation(Proclamation proclamation) {
        Proclamation one = proclamationMapper.selectById(proclamation.getId());
        proclamation.setUsername(one.getUsername());
        proclamation.setCreate_time(one.getCreate_time());
        LocalDateTime now = LocalDateTime.now();
        proclamation.setUpdate_time(now);
        int i = proclamationMapper.updateById(proclamation);
        return i > 0 ? ResultTools.resultMap(true, "更新成功") : ResultTools.resultMap(false, "更新失败");
    }

    @Override
    public Object getOne() {
        Proclamation proclamation = proclamationMapper.selectOne(new QueryWrapper<Proclamation>().orderByDesc("create_time").last("limit 1"));
        return proclamation;
    }

    @Override
    public Object deleteProclamation(Proclamation proclamation) {
        int i = proclamationMapper.deleteById(proclamation);
        return i > 0 ? ResultTools.resultMap(true, "删除成功") : ResultTools.resultMap(false, "删除失败");
    }

    public static final String downloadFilePath = "E:\\data";

    @SneakyThrows
    @Override
    public void exportExcel(QueryProclamationVO queryVO, HttpServletResponse response) {
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (StringTools.isNotEmpty(queryVO.getStartTime())) {
            startTime = LocalDateTime.parse(queryVO.getStartTime(), dtf);
        }
        if (StringTools.isNotEmpty(queryVO.getEndTime())) {
            endTime = LocalDateTime.parse(queryVO.getEndTime(), dtf);
        }
        QueryWrapper<Proclamation> queryWrapper = new QueryWrapper<Proclamation>().ge(startTime != null, "create_time", startTime).le(endTime != null, "create_time", endTime).like(StringTools.isNotEmpty(queryVO.getTitle()), "title", queryVO.getTitle()).orderByDesc("create_time");

        List<Proclamation> result = proclamationMapper.selectList(queryWrapper);
        List<Object> head = ObjectTools.getObjectPropertyList(new Proclamation());
        List<List<Object>> dataList = new ArrayList<>();
        for (Proclamation proclamation : result) {
            dataList.add(ObjectTools.getObjectValueList(proclamation));
        }
        String fileName = "公告列表" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        FileTools.exportFile(head, dataList, downloadFilePath, fileName, "csv", response);
    }
}
