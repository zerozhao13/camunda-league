package com.phoenix.workflow.service.domain.task;

import com.phoenix.workflow.enums.PnxProcessEngine;
import org.camunda.bpm.engine.TaskService;

/**
 * @author zerozhao
 * @version 1.0
 * @title: PnxTaskGroupServiceImpl
 * @projectName phoenix-camunda
 * @description: TODO
 * @date 2020/11/1716:03
 */
public class PnxTaskGroupServiceImpl {
    private final TaskService taskService = PnxProcessEngine.INSTANCE.getProcessEngine().getTaskService();
}
