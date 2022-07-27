package com.example.activiti.controller;

import com.example.activiti.vo.ProcessVO;
import com.example.activiti.vo.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 查询控制器
 *
 * @author laughlook
 * @date 2022/07/26
 */
@RestController
@RequestMapping("/query")
@Api(tags = "查询流程")
@Slf4j
public class QueryController {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RuntimeService runtimeService;


    /**
     * 查询流程任务列表
     * 查询某一次流程的执行一共经历了多少个任务
     *
     * @param processVO 流程入参
     */
    @ApiOperation(value = "查询流程任务列表")
    @RequestMapping(value = "/queryProcessTaskList", method = RequestMethod.POST)
    public Object queryProcessTaskList(@RequestBody ProcessVO processVO) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<HistoricActivityInstance> haiList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processVO.getInstanceId()).orderByHistoricActivityInstanceStartTime().asc().list();
        List<Comment> commentList = taskService.getProcessInstanceComments(processVO.getInstanceId());
        Map<String, String> commentIdMap = new HashMap<>();
        for (Comment comment : commentList) {
            commentIdMap.put(comment.getTaskId(), comment.getId());
        }

        for (HistoricActivityInstance hai : haiList) {
            Map<String, Object> map = new HashMap<>();

            map.put("id", hai.getId());
            map.put("activityId", hai.getActivityId());
            map.put("activityName", hai.getActivityName());
            map.put("activityType", hai.getActivityType());
            map.put("processDefinitionId", hai.getProcessDefinitionId());
            map.put("processInstanceId", hai.getProcessInstanceId());
            map.put("executionId", hai.getExecutionId());
            map.put("taskId", hai.getTaskId());
            map.put("calledProcessInstanceId", hai.getCalledProcessInstanceId());
            map.put("assignee", hai.getAssignee());
            map.put("startTime", hai.getStartTime());
            map.put("endTime", hai.getEndTime());
            map.put("durationInMillis", hai.getDurationInMillis());
            map.put("deleteReason", hai.getDeleteReason());
            map.put("tenantId", hai.getTenantId());
            map.put("time", hai.getTime());

            if (commentIdMap.containsKey(hai.getTaskId())){
                String commentId = commentIdMap.get(hai.getTaskId());
                Comment comment = taskService.getComment(commentId);
                String message = comment.getFullMessage();
                map.put("comment", message);
            }

            result.add(map);
        }
        return new ResultBody<>(result);
    }

    @ApiOperation(value = "查询流程变量")
    @RequestMapping(value = "/queryVariables", method = RequestMethod.POST)
    public Object queryVariables(@RequestBody ProcessVO processVO) {
        Map<String, Object> result = new HashMap<>();
        List<HistoricVariableInstance> hviList = historyService.createHistoricVariableInstanceQuery().processInstanceId(processVO.getInstanceId()).list();
        for (HistoricVariableInstance hvi : hviList) {
            result.put(hvi.getVariableName(), hvi.getValue());
        }
        return new ResultBody<>(result);
    }

    @ApiOperation(value = "查询评论")
    @RequestMapping(value = "/queryComment", method = RequestMethod.POST)
    public Object queryComment(@RequestBody ProcessVO processVO) {
        Comment comment = taskService.getComment(processVO.getCommentId());
        return new ResultBody<>(comment);
    }
}
