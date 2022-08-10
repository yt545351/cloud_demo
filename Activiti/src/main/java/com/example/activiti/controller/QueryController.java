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
import org.activiti.engine.history.HistoricActivityInstanceQuery;
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

    @ApiOperation(value = "获取填写申请任务ID")
    @RequestMapping(value = "/queryTask", method = RequestMethod.POST)
    public Object queryTask(@RequestBody ProcessVO processVO) {
        List<HistoricActivityInstance> haiList = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processVO.getInstanceId())
                .orderByHistoricActivityInstanceStartTime().desc().list();
        String taskId = "startEvent".equals(haiList.get(0).getActivityType()) ? haiList.get(1).getTaskId() : haiList.get(0).getTaskId();
        return new ResultBody<>(taskId);
    }

    @ApiOperation(value = "获取指定责任人，指定流程任务ID")
    @RequestMapping(value = "/queryTaskId", method = RequestMethod.POST)
    public Object queryTaskId(@RequestBody ProcessVO processVO) {
        Task task = taskService.createTaskQuery().
                processDefinitionKey(processVO.getProcessKey()).
                taskId(processVO.getTaskId()).
                taskAssignee(processVO.getAssignee()).
                singleResult();
        return new ResultBody<>();
    }
}
