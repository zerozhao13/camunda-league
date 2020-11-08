package com.phoenix.workflow.service.domain;

import com.phoenix.workflow.enums.PnxProcessEngine;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.camunda.bpm.engine.ProcessEngines.init;

/**
 * @author ZeroZhao
 * @version 1.0
 * @title: IdentityService
 * @projectName phoenix-camunda
 * @description: 用于对Camunda的用户进行操作，提供方法供bizService层调用。
 * @date 2020/11/814:53
 */
@Service
public class PnxIdentityService {

    ProcessEngine processEngine = PnxProcessEngine.INSTANCE.getProcessEngine();

    public IdentityService getIdentityService() {
        System.out.println(processEngine.getIdentityService());
        return processEngine.getIdentityService();
    }
}
