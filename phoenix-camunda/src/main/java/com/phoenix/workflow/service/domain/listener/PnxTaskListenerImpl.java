package com.phoenix.workflow.service.domain.listener;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

/**
 * @author zerozhao
 * @version 1.0
 * @title: PnxTaskListenerImpl
 * @projectName phoenix-camunda
 * @description: TODO 我们可以通过监听器，在这里获取下一个任务的指派人，并将其设置进去。
 * @date 2020/11/1623:08
 */
public class PnxTaskListenerImpl implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        String eventName = delegateTask.getEventName();
        int priority = delegateTask.getPriority();
        if ("create".equals(eventName)) {
            delegateTask.setAssignee("user");
        }
    }
}
