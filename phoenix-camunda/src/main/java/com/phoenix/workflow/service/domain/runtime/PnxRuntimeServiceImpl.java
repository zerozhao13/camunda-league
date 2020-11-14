package com.phoenix.workflow.service.domain.runtime;

import com.phoenix.workflow.enums.PnxProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public ProcessInstance startProcess(String key) {
        return runtimeService.startProcessInstanceByKey(key);
    }

    public List<ProcessInstance> getProcessInstances(int first, int max) {
        return runtimeService.createProcessInstanceQuery().listPage(first, max);
    }

    public void updateTask() {

    }
}
