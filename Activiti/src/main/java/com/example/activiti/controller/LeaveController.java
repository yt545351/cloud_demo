package com.example.activiti.controller;

import com.example.activiti.vo.ProcessVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 离开控制器
 *
 * @author laughlook
 * @date 2022/07/27
 */
@RestController
@RequestMapping("/Leave")
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

    @ApiOperation(value = "启动流程")
    @RequestMapping(value = "/startProcess", method = RequestMethod.POST)
    public void startProcess(@RequestBody ProcessVO processVO) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("assignee", processVO.getAssignee());
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processVO.getProcessKey(), variables);
    }
}
