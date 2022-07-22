package com.example.activiti.controller;

import com.example.activiti.vo.ProcessVO;
import com.example.activiti.vo.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

/**
 * 部署控制器
 *
 * @author laughlook
 * @date 2022/07/22
 */
@RestController
@RequestMapping("/deploy")
@Api(tags = "部署流程")
@Slf4j
public class DeployController {
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @ApiOperation(value = "部署流程")
    @RequestMapping(value = "/deployProcess", method = RequestMethod.POST)
    public void deployProcess() {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("bpmn/Leave.bpmn20.xml")
                .addClasspathResource("bpmn/Leave.png")
                .name("请假流程")
                .deploy();
        log.info("流程ID:{}", deployment.getId());
        log.info("流程Name:{}", deployment.getName());
    }

    @ApiOperation(value = "流程定义信息查询")
    @RequestMapping(value = "/queryProcessDefinition", method = RequestMethod.POST)
    public Object queryProcessDefinition() {
        List<ProcessDefinition> pdList = repositoryService.createProcessDefinitionQuery()
//                .processDefinitionKey("Leave")
                .orderByProcessDefinitionVersion()
                .desc()
                .list();
        List<Map<String, Object>> result = new ArrayList<>();
        for (ProcessDefinition pd : pdList) {
            Map<String, Object> map = new HashMap<>();

            map.put("id", pd.getId());
            log.info("流程定义Id:{}", pd.getId());

            map.put("category", pd.getCategory());
            log.info("流程定义Category:{}", pd.getCategory());

            map.put("name", pd.getName());
            log.info("流程定义Name:{}", pd.getName());

            map.put("key", pd.getKey());
            log.info("流程定义Key:{}", pd.getKey());

            map.put("description", pd.getDescription());
            log.info("流程说明:{}", pd.getDescription());

            map.put("version", pd.getVersion());
            log.info("流程定义Version:{}", pd.getVersion());

            map.put("resourceName", pd.getResourceName());
            log.info("流程定义ResourceName:{}", pd.getResourceName());

            map.put("deploymentId", pd.getDeploymentId());
            log.info("流程部署ID:{}", pd.getDeploymentId());

            map.put("diagramResourceName", pd.getDiagramResourceName());
            log.info("流程定义DiagramResourceName:{}", pd.getDiagramResourceName());

            map.put("hasStartFormKey", pd.hasStartFormKey());
            log.info("HasStartFormKey:{}", pd.hasStartFormKey());

            map.put("hasGraphicalNotation", pd.hasGraphicalNotation());
            log.info("HasGraphicalNotation:{}", pd.hasGraphicalNotation());

            map.put("isSuspended", pd.isSuspended());
            log.info("流程实例是否停止:{}", pd.isSuspended());

            map.put("tenantId", pd.getTenantId());
            log.info("TenantId:{}", pd.getTenantId());

            map.put("engineVersion", pd.getEngineVersion());
            log.info("EngineVersion:{}", pd.getEngineVersion());
            result.add(map);
        }

        return new ResultBody<>(result);
    }

    @ApiOperation(value = "查询流程下正在跑的任务")
    @RequestMapping(value = "/queryProcessInstance", method = RequestMethod.POST)
    public Object queryProcessInstance() {
        List<ProcessInstance> piList = runtimeService.createProcessInstanceQuery()
                .processDefinitionKey("Leave")
                .list();
        List<Map<String, Object>> result = new ArrayList<>();
        for (ProcessInstance pi : piList) {
            Map<String, Object> map = new HashMap<>();

            log.info("流程定义Id:{}", pi.getProcessDefinitionId());
            map.put("processDefinitionId", pi.getProcessDefinitionId());

            log.info("流程定义名称:{}", pi.getProcessDefinitionName());
            map.put("processDefinitionName", pi.getProcessDefinitionName());

            log.info("流程定义Key:{}", pi.getProcessDefinitionKey());
            map.put("processDefinitionKey", pi.getProcessDefinitionKey());

            log.info("流程定义Version:{}", pi.getProcessDefinitionVersion());
            map.put("processDefinitionVersion", pi.getProcessDefinitionVersion());

            log.info("流程部署Id:{}", pi.getDeploymentId());
            map.put("deploymentId", pi.getDeploymentId());

            log.info("businessKey:{}", pi.getBusinessKey());
            map.put("businessKey", pi.getBusinessKey());

            log.info("流程实例是否停止:{}", pi.isSuspended());
            map.put("isSuspended", pi.isSuspended());

            log.info("流程变量:{}", pi.getProcessVariables());
            map.put("processVariables", pi.getProcessVariables());

            log.info("TenantId:{}", pi.getTenantId());
            map.put("tenantId", pi.getTenantId());

            log.info("流程名称:{}", pi.getName());
            map.put("name", pi.getName());

            log.info("流程说明:{}", pi.getDescription());
            map.put("description", pi.getDescription());

            log.info("流程局部名称:{}", pi.getLocalizedName());
            map.put("localizedName", pi.getLocalizedName());

            log.info("流程局部说明:{}", pi.getLocalizedDescription());
            map.put("localizedDescription", pi.getLocalizedDescription());

            log.info("开始时间:{}", pi.getStartTime());
            map.put("startTime", pi.getStartTime());

            log.info("开始用户Id:{}", pi.getStartUserId());
            map.put("startUserId", pi.getStartUserId());
            result.add(map);
        }
        return new ResultBody<>(result);
    }

    @ApiOperation(value = "删除流程部署，部署后的流程可删除")
    @RequestMapping(value = "/deleteDeployment", method = RequestMethod.POST)
    public void deleteDeployment(@RequestBody ProcessVO processVO) {
        //根据流程部署ID，从数据库中删除流程部署。用于废弃的流程删除部署，后续不会再开启流程
        repositoryService.deleteDeployment(processVO.getDeploymentId());
        //如果当前流程部署下有流程实例还没有跑完，是不允许删除的，会报错。这里可强制删除，内部会级联删除，把相关信息都会删除。
//        repositoryService.deleteDeployment(processVO.getDeploymentId(), true);
    }

    @ApiOperation(value = "流程资源下载")
    @RequestMapping(value = "/loadBpmnFile", method = RequestMethod.POST)
    public void loadBpmnFile(@RequestBody ProcessVO processVO) throws IOException {
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processVO.getName())
                .singleResult();
        String deploymentId = pd.getDeploymentId();
        InputStream pngInput = repositoryService.getResourceAsStream(deploymentId, pd.getDiagramResourceName());
        InputStream bpmnInput = repositoryService.getResourceAsStream(deploymentId, pd.getResourceName());
        FileOutputStream pngOut = new FileOutputStream(new File("E:\\File\\Leave.png"));
        FileOutputStream bpmnOut = new FileOutputStream(new File("E:\\File\\Leave.bpmn"));
        IOUtils.copy(pngInput, pngOut);
        IOUtils.copy(bpmnInput, bpmnOut);
        pngInput.close();
        bpmnInput.close();
        pngOut.close();
        bpmnOut.close();
    }
}
