package com.example.system.vo;

import lombok.Data;

/**
 * 查询用户信息入参
 *
 * @author laughlook
 * @date 2022/07/21
 */
@Data
public class QueryUserInfoVO extends Page {
    private String username;
    private String role_id;
}
