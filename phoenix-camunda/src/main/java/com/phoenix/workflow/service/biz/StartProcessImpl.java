package com.phoenix.workflow.service.biz;

import com.phoenix.workflow.service.domain.identity.PnxIdentityService;
import com.phoenix.workflow.service.domain.process.PnxProcessEngine;
import com.phoenix.workflow.service.domain.runtime.PnxRuntimeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zerozhao
 * @version 1.0
 * @title: StartProcessImpl
 * @projectName phoenix-camunda
 * @description: TODO
 * @date 2020/11/1519:26
 */
@Service
public class StartProcessImpl {
    @Autowired
    PnxIdentityService pnxIdentityService;
    @Autowired
    PnxRuntimeServiceImpl pnxRuntimeService;

    /**
     * 指定启动用户的开启流程
     * @param authUser
     * @param processKey
     */
    public void startProcess(String authUser, String processKey) {
        pnxIdentityService.setAuthUser(authUser);
        pnxRuntimeService.startProcess(processKey);
    }
}
