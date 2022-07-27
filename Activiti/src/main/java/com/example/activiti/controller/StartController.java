package com.example.activiti.controller;

import com.example.activiti.vo.ProcessVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 启动控制器
 *
 * @author laughlook
 * @date 2022/07/25
 */
@RestController
@RequestMapping("/start")
@Api(tags = "开启流程")
@Slf4j
public class StartController {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RuntimeService runtimeService;

    @ApiOperation(value = "启动流程")
    @RequestMapping(value = "/startProcess", method = RequestMethod.POST)
    public void startProcess(@RequestBody ProcessVO processVO) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("assignee", processVO.getAssignee());
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processVO.getProcessKey(), variables);
//        printNextProcessInfo(processInstance.getId());
    }

    @ApiOperation(value = "填写申请")
    @RequestMapping(value = "/fillInfo", method = RequestMethod.POST)
    public void fillInfo(@RequestBody ProcessVO processVO) {
        Task task = taskService.createTaskQuery().processDefinitionKey(processVO.getProcessKey()).taskId(processVO.getTaskId()).taskAssignee(processVO.getAssignee()).singleResult();

        //添加备注 addComment(taskId,processInstanceId,message)
        taskService.addComment(task.getId(), task.getProcessInstanceId(), processVO.getComment());
        //添加claim
//        taskService.claim("","");
        //添加附件
//        taskService.createAttachment("","");
        taskService.complete(task.getId(), processVO.getVariables());
        printNextProcessInfo(task.getProcessInstanceId());
    }


    @ApiOperation(value = "查询候选任务列表")
    @RequestMapping(value = "/queryCandidateTaskList", method = RequestMethod.POST)
    public void queryCandidateTaskList(@RequestBody ProcessVO processVO) {
        List<Task> candidateTasks = taskService.createTaskQuery().processDefinitionKey(processVO.getProcessKey()).taskCandidateGroup(processVO.getTaskCandidateGroup()).list();
        for (Task task : candidateTasks) {
            System.out.println(task);
        }
    }

    @ApiOperation(value = "认领任务")
    @RequestMapping(value = "/claimTask", method = RequestMethod.POST)
    public void claimTask(@RequestBody ProcessVO processVO) {
        taskService.claim(processVO.getTaskId(), processVO.getCandidateUser());
        log.info("候选人：{}-已认领任务：{}", processVO.getCandidateUser(), processVO.getTaskId());
    }

    @ApiOperation(value = "获取任务列表")
    @RequestMapping(value = "/queryTaskList", method = RequestMethod.POST)
    public void queryTaskList(@RequestBody ProcessVO processVO) {
        List<Task> tasks = taskService.createTaskQuery().processDefinitionKey(processVO.getProcessKey()).taskAssignee(processVO.getAssignee()).list();
        for (Task task : tasks) {
            log.info("流程定义ID：{}", task.getProcessDefinitionId());
            log.info("任务ID：{}", task.getId());
            log.info("任务负责人:{}", task.getAssignee());
            log.info("任务名称:{}", task.getName());
        }
    }

    @ApiOperation(value = "完成任务")
    @RequestMapping(value = "/completeTask", method = RequestMethod.POST)
    public void completeTask(@RequestBody ProcessVO processVO) {
        Task task = taskService.createTaskQuery()
                .taskId(processVO.getTaskId())
                .taskAssignee(processVO.getAssignee())
                .singleResult();
        taskService.addComment(task.getId(), task.getProcessInstanceId(), processVO.getComment());
        taskService.complete(processVO.getTaskId());
        printNextProcessInfo(task.getProcessInstanceId());
    }

    private void printNextProcessInfo(String instanceId) {
        HistoricActivityInstanceQuery instanceQuery = historyService.createHistoricActivityInstanceQuery();
        //根据流程实例ID查询
        instanceQuery.processInstanceId(instanceId);
        instanceQuery.orderByHistoricActivityInstanceStartTime().asc();


        List<HistoricActivityInstance> history = instanceQuery.list();
        for (HistoricActivityInstance hi : history) {
            log.info("activityId:{}", hi.getActivityId());
            log.info("activityName:{}", hi.getActivityName());
            log.info("processDefinitionId:{}", hi.getProcessDefinitionId());
            log.info("processInstanceId:{}", hi.getProcessInstanceId());
            log.info("assignee:{}", hi.getAssignee());
        }
    }
}
