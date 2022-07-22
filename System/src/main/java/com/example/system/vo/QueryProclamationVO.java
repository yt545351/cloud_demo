package com.example.system.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 查询公告入参
 *
 * @author laughlook
 * @date 2022/07/21
 */
@Data
public class QueryProclamationVO extends Page {
    private String startTime;
    private String endTime;
    private String title;
}
