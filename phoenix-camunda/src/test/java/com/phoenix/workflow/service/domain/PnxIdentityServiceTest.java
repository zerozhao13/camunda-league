package com.phoenix.workflow.service.domain;

import com.phoenix.workflow.enums.PnxProcessEngine;
import org.camunda.bpm.engine.ProcessEngine;
import org.junit.jupiter.api.Test;

/**
 * @author zerozhao
 * @version 1.0
 * @title: PnxIdentityServiceTest
 * @projectName phoenix-camunda
 * @description: TODO
 * @date 2020/11/815:33
 */
class PnxIdentityServiceTest {

    ProcessEngine processEngine = PnxProcessEngine.INSTANCE.getProcessEngine();
    @Test
    void getIdentityService() {
        System.out.println(processEngine.getIdentityService());
    }
}