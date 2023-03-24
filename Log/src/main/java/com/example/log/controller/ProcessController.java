package com.example.log.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.*;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.idm.api.User;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * 过程控制器
 *
 * @author laughlook
 * @date 2022/10/24
 */
@RequestMapping
@RestController
@Slf4j
public class ProcessController {
    @Autowired
    private FormService formService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ManagementService managementService;
    @Autowired
    private IdentityService identityService;


    @ApiOperation(value = "测试接口")
    @RequestMapping(value = "/createProcess", method = RequestMethod.GET)
    public void createProcess() {
        runtimeService.startProcessInstanceByKey("leave", "123456", new HashMap<>());
        List<Task> list = taskService.createTaskQuery().processDefinitionKey("123").taskCandidateGroup("123456").list();
    }
}
