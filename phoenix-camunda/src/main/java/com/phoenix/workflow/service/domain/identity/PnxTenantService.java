package com.phoenix.workflow.service.domain.identity;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.impl.persistence.entity.TenantEntity;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zerozhao
 * @version 1.0
 * @title: PnxTenantService
 * @projectName phoenix-camunda
 * @description: TODO
 * @date 2020/11/1121:02
 */
public class PnxTenantService {
    @Autowired
    PnxIdentityService pnxIdentityService;

    private IdentityService identityService = pnxIdentityService.getIdentityService();

    /**
     * 创建一个租户
     * @param id
     * @param name
     */
    public void saveTenant(String id, String name) {
        TenantEntity tenantEntity = new TenantEntity();
        tenantEntity.setId(id);
        tenantEntity.setName(name);
        identityService.saveTenant(tenantEntity);
    }

    /**
     * 将用户添加到租户下
     * @param tenantId
     * @param userId
     */
    public void saveTenantUserMembership(String tenantId, String userId) {
        identityService.createTenantUserMembership(tenantId, userId);
    }

    /**
     * 将组添加到租户下
     * @param tenantId
     * @param groupId
     */
    public void saveTenantGroupMembership(String tenantId, String groupId) {
        identityService.createTenantUserMembership(tenantId, groupId);
    }

}
