package com.phoenix.workflow.service.domain;

import com.phoenix.workflow.service.domain.identity.PnxIdentityService;
import org.camunda.bpm.engine.identity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * @author zerozhao
 * @version 1.0
 * @title: PnxIdentityServiceTest
 * @projectName phoenix-camunda
 * @description: TODO
 * @date 2020/11/815:33
 */
@SpringBootTest
class PnxIdentityServiceTest {

    @Autowired
    PnxIdentityService pnxIdentityService;

    //private UserEntity userEntity;

    @Test
    void getIdentityService() {
        System.out.println(pnxIdentityService);
    }

    @Test
    void addUserWithSalt() {
        String id = "user2";
        String pwd = "pwd";
        pnxIdentityService.addUser(id, "phoenix", "foece 1", "user1@phoenix-force.com", pwd, "salt");
        User user = pnxIdentityService.getUserById(id);
        assertEquals("ID 一致", id, user.getId());
        assertEquals("密码未加密", pwd, user.getPassword());
    }
}