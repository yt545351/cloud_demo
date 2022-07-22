package com.example.system.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 查询时间
 *
 * @author laughlook
 * @date 2022/07/21
 */
@Data
public class QueryTime {
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
}
