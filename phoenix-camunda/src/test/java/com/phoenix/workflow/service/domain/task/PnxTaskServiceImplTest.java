package com.phoenix.workflow.service.domain.task;

import org.camunda.bpm.engine.task.Task;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNull;

/**
 * @author zerozhao
 * @version 1.0
 * @title: PnxTaskServiceImplTest
 * @projectName phoenix-camunda
 * @description: TODO
 * @date 2020/11/1421:50
 */
@SpringBootTest
class PnxTaskServiceImplTest {
    @Autowired
    PnxTaskServiceImpl taskService;

    private String taskId = "1604";
    private final String assignee = "zero";

    @Test
    @Order(0)
    void getTasks() {
        System.out.println(taskService);
        List<Task> tasks = taskService.getTasks(0, 5);
        tasks.stream().forEach(task -> {
            taskId = task.getId();
        });
    }

    @Test
    @Order(1)
    void assignTask() {
        taskService.assignTask(taskId, assignee);
    }

    @Test
    @Order(2)
    void getTasksByAssignee() {
        List<Task> tasks = taskService.getTasksByAssignee(assignee, 0, 5);
        tasks.stream().forEach(task -> {
            assertEquals("assignee is equal.", task.getAssignee(), assignee);
        });
    }

    @Test
    @Order(3)
    void completeTask() {
        taskService.completeTask(taskId);
        Task task = taskService.getTaskById(taskId);
        assertNull("没有对应task", task);
    }
}