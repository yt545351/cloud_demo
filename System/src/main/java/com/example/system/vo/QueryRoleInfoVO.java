package com.example.system.vo;

import lombok.Data;

/**
 * 查询角色信息入参
 *
 * @author laughlook
 * @date 2022/07/21
 */
@Data
public class QueryRoleInfoVO extends Page {
    private String start_time;
    private String end_time;
    private String role_name;
}
