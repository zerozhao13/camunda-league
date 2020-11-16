package com.phoenix.workflow.service.domain.history;

import com.phoenix.workflow.enums.PnxProcessEngine;
import org.camunda.bpm.engine.HistoryService;

/**
 * @author zhaopeng01
 * @version 1.0
 * @title: PnxHistoryServiceImpl
 * @projectName phoenix-camunda
 * @description: TODO
 * @date 2020/11/1515:48
 */
public class PnxHistoryServiceImpl {
    private final HistoryService historyService = PnxProcessEngine.INSTANCE.getProcessEngine().getHistoryService();
}
