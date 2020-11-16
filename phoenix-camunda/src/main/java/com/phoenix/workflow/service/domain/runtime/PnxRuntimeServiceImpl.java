package com.phoenix.workflow.service.domain.runtime;

import com.phoenix.workflow.enums.PnxProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.camunda.bpm.engine.runtime.ProcessInstantiationBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zerozhao
 * @version 1.0
 * @title: PnxRuntimeServiceImpl
 * @projectName phoenix-camunda
 * @description: TODO
 * @date 2020/11/1419:06
 */
@Service
public class PnxRuntimeServiceImpl {

    private final RuntimeService runtimeService = PnxProcessEngine.INSTANCE.getProcessEngine().getRuntimeService();
    private final RepositoryService repositoryService = PnxProcessEngine.INSTANCE.getProcessEngine().getRepositoryService();

    public ProcessInstance startProcess(String key) {
        return runtimeService.startProcessInstanceByKey(key);
    }

    /**
     * 可通过定义的变量传入来启动对应流程
     * 如在流程中定义了变量，如果不在启动时传入，系统会报错
     * 如流程定义中指定用户为 #{userId}, 那么在启动时我们需要通过vars传入userId，放入map中
     *
     * @param key
     * @param vars
     * @return
     */
    public ProcessInstance startProcess(String key, Map<String, Object> vars) {
        return runtimeService.startProcessInstanceByKey(key, vars);
    }

    public List<ProcessInstance> getProcessInstances(int first, int max) {
        return runtimeService.createProcessInstanceQuery().listPage(first, max);
    }

    public ProcessInstance startProcessBeforeTask(String key, String activityId) {
        ProcessInstantiationBuilder processInstantiationBuilder = runtimeService.createProcessInstanceByKey(key);
        return processInstantiationBuilder.startBeforeActivity(activityId).execute();
    }

    public ProcessInstance startProcessAfterTask(String key, String activityId) {
        ProcessInstantiationBuilder processInstantiationBuilder = runtimeService.createProcessInstanceByKey(key);
        return processInstantiationBuilder.startAfterActivity(activityId).execute();
    }

    public ProcessInstance startProcessTrans(String key, String activityId) {
        ProcessInstantiationBuilder processInstantiationBuilder = runtimeService.createProcessInstanceByKey(key);
        return processInstantiationBuilder.startTransition(activityId).execute();
    }

    /**
     * 根据流程实例ID删除流程实例
     *
     * @param processId
     * @param reason
     */
    public void delProcess(String processId, String reason) {
        runtimeService.deleteProcessInstance(processId, reason);
    }

    public void getActiveActivityIds(String id) {
        List<String> ids = runtimeService.getActiveActivityIds(id);
    }

    // 挂起流程实例
    public void susProcess(String id) {
        runtimeService.suspendProcessInstanceById(id);
    }

    /**
     * 挂起流程定义，挂起后基于该定义的流程将不能再执行，除非先激活
     *
     * 两种挂起方式
     *
     * @param id
     */
    public void suspendProcessDef(String id) {
        //runtimeService.suspendProcessInstanceByProcessDefinitionId(id);
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        processInstanceQuery.processInstanceId(id);
        runtimeService.updateProcessInstanceSuspensionState().byProcessInstanceQuery(processInstanceQuery).suspendAsync();
    }

    /**
     * 两种激活流程定义的方法
     * @param id
     */
    public void activeProcessDef(String id) {
        //repositoryService.activateProcessDefinitionById(id);
        repositoryService
                .updateProcessDefinitionSuspensionState()
                .byProcessDefinitionId(id)
                .includeProcessInstances(true)
                .activate();
    }
}
