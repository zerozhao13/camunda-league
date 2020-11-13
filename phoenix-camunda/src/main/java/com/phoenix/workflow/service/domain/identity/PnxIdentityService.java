package com.phoenix.workflow.service.domain.identity;

import com.phoenix.workflow.enums.PnxProcessEngine;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.identity.UserQuery;
import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZeroZhao
 * @version 1.0
 * @title: IdentityService
 * @projectName phoenix-camunda
 * @description: 用于对Camunda的用户进行操作，提供方法供bizService层调用。
 * @date 2020/11/814:53
 */
@Service
public class PnxIdentityService {

    IdentityService identityService = PnxProcessEngine.INSTANCE.getProcessEngine().getIdentityService();

    public IdentityService getIdentityService() {
        return this.identityService;
    }

    /**
     * 由camunda自动为数据库加密加盐
     *
     * @param id
     * @param firstname
     * @param lastname
     * @param email
     * @param pwd
     * @return
     */
    public String addUser(String id, String firstname, String lastname, String email, String pwd) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setFirstName(firstname);
        userEntity.setLastName(lastname);
        userEntity.setEmail(email);
        userEntity.setPassword(pwd);
        identityService.saveUser(userEntity);
        return id;
    }

    /**
     * 将用户密码直接存入数据库，一般不建议这么使用
     *
     * @param id
     * @param firstname
     * @param lastname
     * @param email
     * @param pwd
     * @param salt
     * @return
     */
    public String addUser(String id, String firstname, String lastname, String email, String pwd, String salt) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setFirstName(firstname);
        userEntity.setLastName(lastname);
        userEntity.setEmail(email);
        userEntity.setDbPassword(pwd);
        userEntity.setSalt(salt);
        identityService.saveUser(userEntity);
        return id;
    }

    /**
     * 根据id获取用户
     * @param id
     * @return
     */
    public User getUserById(String id) {
        User user = identityService.createUserQuery().userId(id).singleResult();
        return user;
    }

    /**
     * 获取用户列表
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<User> getUsers(int firstResult, int maxResult) {
        UserQuery userQuery = identityService.createUserQuery();
        return identityService.createUserQuery().listPage(firstResult, maxResult);
    }

    /**
     * 根据用户id删除用户
     * @param userId
     */
    public void delUser(String userId) {
        identityService.deleteUser(userId);
    }

    /**
     * 获取租户下的用户列表
     * @param tenantId
     * @param first
     * @param max
     * @return
     */
    public List<User> getUsersFromTenant(String tenantId, int first, int max) {
        return identityService.createUserQuery().memberOfTenant(tenantId).listPage(first, max);
    }
}
