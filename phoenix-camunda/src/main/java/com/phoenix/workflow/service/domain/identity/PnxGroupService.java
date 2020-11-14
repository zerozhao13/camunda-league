package com.phoenix.workflow.service.domain.identity;

import com.phoenix.workflow.enums.PnxProcessEngine;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.GroupQuery;
import org.camunda.bpm.engine.impl.persistence.entity.GroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhaopeng01
 * @version 1.0
 * @title: PnxGroupService
 * @projectName phoenix-camunda
 * @description: TODO
 * @date 2020/11/1121:01
 */
@Service
public class PnxGroupService {

    private final IdentityService identityService = PnxProcessEngine.INSTANCE.getProcessEngine().getIdentityService();

    /**
     * 新增组
     * @param id
     * @param name
     * @param rev
     * @param type
     */
    public void addGroup(String id, String name, int rev, String type) {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setId(id);
        groupEntity.setName(name);
        groupEntity.setRevision(rev);
        groupEntity.setType(type);
        identityService.saveGroup(groupEntity);
    }

    /**
     * 将用户添加到组
     * @param userId
     * @param groupId
     */
    public void createMembership(String userId, String groupId) {
        identityService.createMembership(userId, groupId);
    }

    /**
     * 根据分页获取组信息
     * @param first
     * @param max
     * @return
     */
    public List<Group> getGroups(int first, int max) {
        GroupQuery groupQuery = identityService.createGroupQuery();
        return groupQuery.listPage(first, max);
    }

    /**
     * 根据组ID删除组
     * 可先获取组列表，再根据获取的ID删除
     * @param groupId
     */
    public void delGroup(String groupId) {
        identityService.deleteGroup(groupId);
    }

    /**
     * 将用户从组中删除
     * @param userId
     * @param groupId
     */
    public void delMembership(String userId, String groupId) {
        identityService.deleteMembership(userId, groupId);
    }

    /**
     * 获取租户下的组
     * @param tenantId
     * @param first
     * @param max
     * @return
     */
    public List<Group> getGroupsFromTenant(String tenantId, int first, int max) {
        return identityService.createGroupQuery().memberOfTenant(tenantId).listPage(first, max);
    }
}
