package com.phoenix.workflow.service.domain.runtime;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zerozhao
 * @version 1.0
 * @title: PnxRuntimeServiceImplTest
 * @projectName phoenix-camunda
 * @description: TODO
 * @date 2020/11/1420:52
 */
@SpringBootTest
class PnxRuntimeServiceImplTest {

    @Autowired
    PnxRuntimeServiceImpl pnxRuntimeService;

    @Test
    void startProcess() {
        String key = "Process_0iplu3k";
        ProcessInstance processInstance = pnxRuntimeService.startProcess(key);
        processInstance.getId();
        System.out.println("id is : " + processInstance.getId());
        System.out.println("getBusinessKey is : " + processInstance.getBusinessKey());
        System.out.println("getRootProcessInstanceId is : " + processInstance.getRootProcessInstanceId());
        System.out.println("getProcessInstanceId is : " + processInstance.getProcessInstanceId());
    }

    @Test
    void getProcessInstances() {
        int first = 0;
        int max = 10;
        List<ProcessInstance> pis = pnxRuntimeService.getProcessInstances(first, max);
        for (ProcessInstance pi : pis) {
            System.out.println("======================");
            System.out.println("id is : " + pi.getId());
            System.out.println("getBusinessKey is : " + pi.getBusinessKey());
            System.out.println("getRootProcessInstanceId is : " + pi.getRootProcessInstanceId());
            System.out.println("getProcessInstanceId is : " + pi.getProcessInstanceId());
        }
    }
}