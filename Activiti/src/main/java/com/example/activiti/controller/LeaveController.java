package com.example.activiti.controller;

import com.example.activiti.util.ResultUtils;
import com.example.activiti.util.StringUtils;
import com.example.activiti.vo.PageBean;
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
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 离开控制器
 *
 * @author laughlook
 * @date 2022/07/27
 */
@RestController
@RequestMapping("/leave")
@Api(tags = "开启流程")
@Slf4j
public class LeaveController {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RuntimeService runtimeService;

    @ApiOperation(value = "历史任务列表")
    @RequestMapping(value = "/historyProcessList", method = RequestMethod.POST)
    public Object historyProcessList(@RequestBody ProcessVO processVO) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<HistoricProcessInstance> hpiList = new ArrayList<>();
        if (StringUtils.isNotEmpty(processVO.getProcessKey())) {
            hpiList = historyService.createHistoricProcessInstanceQuery().processDefinitionKey(processVO.getProcessKey()).list();
        } else {
            hpiList = historyService.createHistoricProcessInstanceQuery().list();

        }
        for (HistoricProcessInstance hpi : hpiList) {
            List<HistoricVariableInstance> hviList = historyService.createHistoricVariableInstanceQuery().processInstanceId(hpi.getId()).list();
            for (HistoricVariableInstance hvi : hviList) {
                if (hvi.getValue().equals(processVO.getAssignee())) {
                    Map<String, Object> map = new HashMap<>();

                    map.put("id", hpi.getId());
                    log.info("id:{}", hpi.getId());

                    map.put("businessKey", hpi.getBusinessKey());
                    log.info("BusinessKey:{}", hpi.getBusinessKey());

                    map.put("processDefinitionId", hpi.getProcessDefinitionId());
                    log.info("ProcessDefinitionId:{}", hpi.getProcessDefinitionId());

                    map.put("processDefinitionName", hpi.getProcessDefinitionName());
                    log.info("ProcessDefinitionName:{}", hpi.getProcessDefinitionName());

                    map.put("processDefinitionKey", hpi.getProcessDefinitionKey());
                    log.info("ProcessDefinitionKey:{}", hpi.getProcessDefinitionKey());

                    map.put("processDefinitionVersion", hpi.getProcessDefinitionVersion());
                    log.info("ProcessDefinitionVersion:{}", hpi.getProcessDefinitionVersion());

                    map.put("deploymentId", hpi.getDeploymentId());
                    log.info("DeploymentId:{}", hpi.getDeploymentId());

                    map.put("startTime", hpi.getStartTime());
                    log.info("StartTime:{}", hpi.getStartTime());

                    map.put("endTime", hpi.getEndTime());
                    log.info("EndTime:{}", hpi.getEndTime());

                    map.put("durationInMillis", hpi.getDurationInMillis());
                    log.info("DurationInMillis:{}", hpi.getDurationInMillis());

                    map.put("endActivityId", hpi.getEndActivityId());
                    log.info("EndActivityId:{}", hpi.getEndActivityId());

                    map.put("startUserId", hpi.getStartUserId());
                    log.info("StartUserId:{}", hpi.getStartUserId());

                    map.put("startActivityId", hpi.getStartActivityId());
                    log.info("StartActivityId:{}", hpi.getStartActivityId());

                    map.put("deleteReason", hpi.getDeleteReason());
                    log.info("DeleteReason:{}", hpi.getDeleteReason());

                    map.put("superProcessInstanceId", hpi.getSuperProcessInstanceId());
                    log.info("SuperProcessInstanceId:{}", hpi.getSuperProcessInstanceId());

                    map.put("tenantId", hpi.getTenantId());
                    log.info("TenantId:{}", hpi.getTenantId());

                    map.put("name", hpi.getName());
                    log.info("Name:{}", hpi.getName());

                    map.put("description", hpi.getDescription());
                    log.info("Description:{}", hpi.getDescription());

                    map.put("processVariables", hpi.getProcessVariables());
                    log.info("ProcessVariables:{}", hpi.getProcessVariables());

                    map.put("assignee", hvi.getValue());

                    resultList.add(map);
                }
            }

        }
        PageBean<Map<String, Object>> result = new PageBean<>(resultList, processVO.getPageNum(), processVO.getPageSize());
        return new ResultBody<>(result);
    }

    @ApiOperation(value = "启动流程,并填写申请")
    @RequestMapping(value = "/startProcess", method = RequestMethod.POST)
    public Object startProcess(@RequestBody ProcessVO processVO) {
        //申请责任人
        String assignee = processVO.getAssignee();
        //责任人变量
        Map<String, Object> assigneeMap = new HashMap<>();
        assigneeMap.put("assignee", assignee);
        //流程键
        String processKey = processVO.getProcessKey();
        //备注
        String comment = processVO.getComment();
        //流程变量
        Map<String, Object> variables = processVO.getVariables();
        try {
            //启动流程，添加责任人变量
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processVO.getProcessKey(), assigneeMap);
            //获取下一个任务ID
            HistoricActivityInstance hai = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId()).orderByHistoricActivityInstanceStartTime().desc().list().get(0);
            String taskId = hai.getTaskId();
            //获取任务
            Task task = taskService.createTaskQuery().processDefinitionKey(processKey).taskId(taskId).taskAssignee(assignee).singleResult();

            //添加备注 addComment(taskId,processInstanceId,message)
            taskService.addComment(task.getId(), task.getProcessInstanceId(), comment);
            //完成任务
            taskService.complete(task.getId(), variables);

        } catch (Exception e) {
            log.info("启动流程失败");
            return new ResultBody<>(ResultUtils.resultMap(false, "申请失败,请联系管理员"));
        }
        log.info("启动流程,并填写申请成功");
        return new ResultBody<>(ResultUtils.resultMap(true, "申请成功"));
    }

    @ApiOperation(value = "查询候选任务列表")
    @RequestMapping(value = "/queryCandidateTaskList", method = RequestMethod.POST)
    public Object queryCandidateTaskList(@RequestBody ProcessVO processVO) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        String processKey = processVO.getProcessKey();
        List<Task> candidateTasks = new ArrayList<>();
        if (StringUtils.isNotEmpty(processKey)) {
            candidateTasks = taskService.createTaskQuery().processDefinitionKey(processVO.getProcessKey()).taskCandidateGroup(processVO.getTaskCandidateGroup()).list();
        } else {
            candidateTasks = taskService.createTaskQuery().taskCandidateGroup(processVO.getTaskCandidateGroup()).list();
        }

        for (Task task : candidateTasks) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", task.getId());
            map.put("name", task.getName());
            map.put("description", task.getDescription());
            map.put("priority", task.getPriority());
            map.put("owner", task.getOwner());
            map.put("assignee", task.getAssignee());
            map.put("processInstanceId", task.getProcessInstanceId());
            map.put("executionId", task.getExecutionId());
            map.put("processDefinitionId", task.getProcessDefinitionId());
            map.put("createTime", task.getCreateTime());
            map.put("taskDefinitionKey", task.getTaskDefinitionKey());
            map.put("dueDate", task.getDueDate());
            map.put("category", task.getCategory());
            map.put("parentTaskId", task.getParentTaskId());
            map.put("tenantId", task.getTenantId());
            map.put("formKey", task.getFormKey());
            map.put("taskLocalVariables", task.getTaskLocalVariables());
            map.put("processVariables", task.getProcessVariables());
            map.put("claimTime", task.getClaimTime());

            //获取流程变量
            List<HistoricVariableInstance> hviList = historyService.createHistoricVariableInstanceQuery().processInstanceId(task.getProcessInstanceId()).list();
            for (HistoricVariableInstance hvi : hviList) {
                map.put(hvi.getVariableName(), hvi.getValue());
            }

            //获取流程定义Name
            ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
            map.put("processName", pd.getName());

            resultList.add(map);
        }
        PageBean<Map<String, Object>> result = new PageBean<>(resultList, processVO.getPageNum(), processVO.getPageSize());
        return new ResultBody<>(result);
    }

    @ApiOperation(value = "认领任务")
    @RequestMapping(value = "/claimTask", method = RequestMethod.POST)
    public Object claimTask(@RequestBody ProcessVO processVO) {
        try {
            taskService.claim(processVO.getTaskId(), processVO.getCandidateUser());
            log.info("候选人：{}-已认领任务：{}", processVO.getCandidateUser(), processVO.getTaskId());
        } catch (Exception e) {
            return new ResultBody<>(ResultUtils.resultMap(true, "认领申请失败"));
        }
        return new ResultBody<>(ResultUtils.resultMap(true, "认领申请成功"));
    }

    @ApiOperation(value = "获取任务列表")
    @RequestMapping(value = "/queryTaskList", method = RequestMethod.POST)
    public Object queryTaskList(@RequestBody ProcessVO processVO) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
        if (StringUtils.isNotEmpty(processVO.getProcessKey())) {
            tasks = taskService.createTaskQuery().processDefinitionKey(processVO.getProcessKey()).taskAssignee(processVO.getAssignee()).list();
        } else {
            tasks = taskService.createTaskQuery().taskAssignee(processVO.getAssignee()).list();
        }

        for (Task task : tasks) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", task.getId());
            map.put("name", task.getName());
            map.put("description", task.getDescription());
            map.put("priority", task.getPriority());
            map.put("owner", task.getOwner());
            map.put("assignee", task.getAssignee());
            map.put("processInstanceId", task.getProcessInstanceId());
            map.put("executionId", task.getExecutionId());
            map.put("processDefinitionId", task.getProcessDefinitionId());
            map.put("createTime", task.getCreateTime());
            map.put("taskDefinitionKey", task.getTaskDefinitionKey());
            map.put("dueDate", task.getDueDate());
            map.put("category", task.getCategory());
            map.put("parentTaskId", task.getParentTaskId());
            map.put("tenantId", task.getTenantId());
            map.put("formKey", task.getFormKey());
            map.put("taskLocalVariables", task.getTaskLocalVariables());
            map.put("processVariables", task.getProcessVariables());
            map.put("claimTime", task.getClaimTime());

            //获取流程变量
            List<HistoricVariableInstance> hviList = historyService.createHistoricVariableInstanceQuery().processInstanceId(task.getProcessInstanceId()).list();
            for (HistoricVariableInstance hvi : hviList) {
                map.put(hvi.getVariableName(), hvi.getValue());
            }

            //获取流程定义Name
            ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
            map.put("processName", pd.getName());

            resultList.add(map);
        }
        PageBean<Map<String, Object>> result = new PageBean<>(resultList, processVO.getPageNum(), processVO.getPageSize());
        return new ResultBody<>(result);
    }

    @ApiOperation(value = "完成任务")
    @RequestMapping(value = "/completeTask", method = RequestMethod.POST)
    public Object completeTask(@RequestBody ProcessVO processVO) {
        try {
            Task task = taskService.createTaskQuery()
                    .taskId(processVO.getTaskId())
                    .taskAssignee(processVO.getAssignee())
                    .singleResult();
            taskService.addComment(task.getId(), task.getProcessInstanceId(), processVO.getComment());
            taskService.complete(processVO.getTaskId());
        } catch (Exception e) {
            return new ResultBody<>(ResultUtils.resultMap(false, "提交失败，请联系系统管理员"));
        }
        return new ResultBody<>(ResultUtils.resultMap(true, "提交成功"));
    }
}
