package com.phoenix.workflow.service.domain.task;

import com.phoenix.workflow.enums.PnxProcessEngine;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.IdentityLink;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhaopeng01
 * @version 1.0
 * @title: PnxTaskServiceImpl
 * @projectName phoenix-camunda
 * @description: TODO
 * @date 2020/11/1421:41
 */
@Service
public class PnxTaskServiceImpl {
    private final TaskService taskService = PnxProcessEngine.INSTANCE.getProcessEngine().getTaskService();

    public List<Task> getTasks(int first, int max) {
        return taskService.createTaskQuery().listPage(first, max);
    }

    public void assignTask(String taskId, String assignee) {
        taskService.setAssignee(taskId, assignee);
    }

    public List<Task> getTasksByAssignee(String assignee, int first, int max) {
        return taskService.createTaskQuery().taskAssignee(assignee).listPage(first, max);
    }

    public void completeTask(String taskId) {
        taskService.complete(taskId);
    }

    public Task getTaskById(String taskId) {
        return taskService.createTaskQuery().taskId(taskId).singleResult();
    }

    public List<Task> getTasksByCandidate(String userId) {
        return taskService.createTaskQuery().taskCandidateUser(userId).list();
    }

    /**
     * 获取哪些人可以处理当前任务
     * @param taskId
     * @return
     */
    public List<IdentityLink> getIdentityTasks(String taskId) {
        return taskService.getIdentityLinksForTask(taskId);
    }

    /**
     * 候选人认领任务
     * 如果userId为空，任务会重新恢复到待签收状态
     * @param taskId
     * @param userId
     */
    public void claim(String taskId, String userId) {
        taskService.claim(taskId, userId);
    }
}
