package com.example.activiti.vo;

import lombok.Data;

import java.util.Map;

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
     * 实例id
     */
    private String instanceId;

    /**
     * 流程名称
     */
    private String name;

    /**
     * 流程键名
     */
    private String processKey;

    /**
     * 流程变量
     */
    private Map<String, Object> variables;

    /**
     * 申请责任人
     */
    private String assignee;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 评论
     */
    private String comment;

    /**
     * 候选人用户
     */
    private String candidateUser;

    /**
     * 任务候选人组
     */
    private String taskCandidateGroup;

    /**
     * 评论id
     */
    private String commentId;
}
