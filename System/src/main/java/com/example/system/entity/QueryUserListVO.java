package com.example.system.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryUserListVO {
    @ApiModelProperty(value = "开始时间", example = "2020-08-15 00:00:00")
    public String start_time;
    @ApiModelProperty(value = "结束时间", example = "2020-08-16 00:00:00")
    public String end_time;
    @ApiModelProperty(value = "地市编码(省份为null)", example = "511800")
    public Integer city_id;
    @ApiModelProperty(value = "1:注册 2:呼叫 3:掉话", example = "1")
    public Integer event_type;
    @ApiModelProperty(value = "原因码", example = "408")
    public String cause_code;
    @ApiModelProperty(value = "页码", example = "1")
    public Integer pageNum;
    @ApiModelProperty(value = "条数", example = "10")
    public Integer pageSize;
}
