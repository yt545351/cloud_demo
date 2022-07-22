package com.example.activiti.vo;

import lombok.Data;

/**
 * 流程入参
 *
 * @author laughlook
 * @date 2022/07/22
 */
@Data
public class ProcessVO {
    /**
     * 流程部署id
     */
    private String deploymentId;

    /**
     * 流程名称
     */
    private String name;
}
