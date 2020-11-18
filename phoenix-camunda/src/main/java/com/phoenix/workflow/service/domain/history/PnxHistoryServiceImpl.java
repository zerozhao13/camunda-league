package com.phoenix.workflow.service.domain.history;

import com.phoenix.workflow.enums.PnxProcessEngine;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.history.HistoricIdentityLinkLog;

import java.util.List;

/**
 * @author zerozhao
 * @version 1.0
 * @title: PnxHistoryServiceImpl
 * @projectName phoenix-camunda
 * @description: TODO
 * @date 2020/11/1515:48
 */
public class PnxHistoryServiceImpl {
    private final HistoryService historyService = PnxProcessEngine.INSTANCE.getProcessEngine().getHistoryService();

    /**
     * 返回任务历史处理信息
     * @param taskId
     * @return
     */
    public List<HistoricIdentityLinkLog> getHistoryIdentityLinks(String taskId) {
        return historyService.createHistoricIdentityLinkLogQuery().taskId(taskId).list();
    }
}
