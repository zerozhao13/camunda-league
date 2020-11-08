package com.phoenix.workflow.enums;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;

/**
 * @author zerozhao
 * @version 1.0
 * @title: PnxProcessEngine
 * @projectName phoenix-camunda
 * @description: 返回ProcessEngine的单例，
 * 这里我们没有使用 xml 配置文件，而是使用了java配置的方式，
 * 如果习惯使用xml配置的可以自行参考网上的例子有很多
 * 我们也可以把这些配置信息写入配置文件
 * @date 2020/11/822:28
 */
public enum PnxProcessEngine {
    INSTANCE;
    private ProcessEngine processEngine;
    private PnxProcessEngine () {
        this.processEngine = ProcessEngineConfiguration
                .createStandaloneProcessEngineConfiguration()
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE)
                .setJdbcUrl("jdbc:mariadb://127.0.0.1:3306/phoenix_camunda?useUnicode=true&characterEncoding=utf8mb4&allowMultiQueries=true")
                .setJdbcDriver("org.mariadb.jdbc.Driver")
                .setJdbcUsername("camunda")
                .setJdbcPassword("camunda")
                .setJobExecutorActivate(true)
                .setHistory("full")
                .buildProcessEngine();
    }
    public ProcessEngine getProcessEngine() {
        return processEngine;
    }
}
