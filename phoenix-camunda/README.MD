# Camunda流程引擎系列
<font color=#999AAA >
一、基于Springboot搭建自己的流程引擎中心
<hr style=" border:solid; width:100px; height:1px;" color=#000000 size=1">



@[TOC](文章目录)

</font>

<hr style=" border:solid; width:100px; height:1px;" color=#000000 size=1">

# 前言

现在的业务系统流程越来越多样化，根据不同的数据需要采用不同的策略，如果所有判断逻辑都由开发人员手工编写，对于系统稳定性及开发效率都有一定挑战，这种时候构建一个流程引擎就变得非常重要。

过去的系统，业务逻辑不会太多，而OA系统因为各种审批流会比较复杂，因此流程引擎更多使用于OA系统中，但是现如今的互联网业务下，业务系统的流程复杂程度相比于OA系统可能会更加复杂。

我们这个系列先不从理论入手，直接从实际项目demo开始，再在demo中穿插理论，与我们之前先讲理论最后集中上demo不太一样。

<hr style=" border:solid; width:100px; height:1px;" color=#000000 size=1">


# 一、开始Camunda的基础准备

## Camunda简介
[Camunda](https://camunda.com/)是一个基于JAVA的，支持BPMN、DMN与CMMN的工作流流程引擎。

## 重要概念
[BPMN](https://docs.camunda.org/get-started/bpmn20/) - Business Process Model and Notation (BPMN) is a standard for Workflow and Process Automation. Camunda supports the 2.0 version of BPMN.

[CMMN](https://docs.camunda.org/get-started/cmmn11/) - Case Management Model and Notation (CMMN) is a standard for Case Management. Camunda supports the 1.1 version of CMMN.

[DMN](https://docs.camunda.org/get-started/dmn11/) - Decision Model and Notation (DMN) is a standard for Business Decision Management. Camunda supports the 1.1 version of DMN.

## 系统架构
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201108140730779.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjI4ODIxOQ==,size_16,color_FFFFFF,t_70#pic_center)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201108140750827.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjI4ODIxOQ==,size_16,color_FFFFFF,t_70#pic_center)


# 二、使用步骤
## 1. 下载资源

1. 下载[Modeler](https://camunda.com/download/modeler/)用于进行流程编排；
2. 下载[社区版](https://camunda.com/download/)，获取里面的SQL初始化文件；
3. 通过[https://start.camunda.com](https://start.camunda.com)获取一个初始化的Springboot项目，用于后续开发。

## 2. 引入工程修改pom文件
将通过[https://start.camunda.com](https://start.camunda.com)获取的初始化工程引入到开发工具中，编辑pom.xml文件，我们会发现里面的内容很少，数据库也是使用的h2，我们先来进行依赖的修改。

```xml

<!--
    <dependency>
      <groupId>com.h2database</groupId>
      <artifactId>h2</artifactId>
    </dependency>
-->

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>

    <dependency>
      <groupId>org.mybatis.spring.boot</groupId>
      <artifactId>mybatis-spring-boot-starter</artifactId>
      <version>2.1.3</version>
    </dependency>

    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
    </dependency>

    <dependency>
      <groupId>org.mariadb.jdbc</groupId>
      <artifactId>mariadb-java-client</artifactId>
    </dependency>

    <dependency>
      <groupId>com.baomidou</groupId>
      <artifactId>mybatis-plus-boot-starter</artifactId>
      <version>3.3.2</version>
    </dependency>

```
我们将原本的h2依赖进行了注释，添加了对mariadb以及mybatis plus的依赖。

## 2. 修改配置文件

<font color=#999AAA >代码如下（示例）：



```yaml
server:
  port: 8090
spring:
  datasource:
    name: camunda
    driver-class-name: org.mariadb.jdbc.Driver
    url: jdbc:mariadb://127.0.0.1:3306/phoenix_camunda?useUnicode=true&characterEncoding=utf8mb4&allowMultiQueries=true
    type: com.zaxxer.hikari.HikariDataSource
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5
      username: camunda
      password: camunda
      auto-commit: true
      idle-timeout: 10000
      pool-name: PhoenixCamunda
      max-lifetime: 1800000
      connection-timeout: 11000
      connection-test-query: SELECT 1

camunda.bpm.admin-user:
  id: phoenix
  password: phoenix
```
最后的这段配置

> camunda.bpm.admin-user:
  id: phoenix
  password: phoenix

是我们将服务启动后的默认管理员用户名账号及密码。

## 初始化数据库
找到我们下载资源第二项的文件目录，进入到 \sql\create 目录下，根据你自己使用的数据库类型找到对应脚本。
将脚本在数据库中运行即可，非常简便。


# 启动项目
因为我们是Springboot的项目，在工程中直接启动即可，启动完成后在浏览器中访问 127.0.0.1:8090，如能正常访问，则说明一个基础的Camunda系统已经可以运行。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201108143523602.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjI4ODIxOQ==,size_16,color_FFFFFF,t_70#pic_center)

<font color=#999AAA >二、获取ProcessEngine
<hr style=" border:solid; width:100px; height:1px;" color=#000000 size=1">

</font>

<hr style=" border:solid; width:100px; height:1px;" color=#000000 size=1">

# 前言

ProcessEngine是Camunda流程引擎的基础，我们要进行我们自己Service的编写，或多或少都需要基于ProcessEngine，因此如何正确拿到服务的ProcessEngine会很关键。

<hr style=" border:solid; width:100px; height:1px;" color=#000000 size=1">

<font color=#999AAA >提示：以下是本篇文章正文内容，下面案例可供参考

# 一、编写ProcessEngine枚举


<font color=#999AAA >枚举是单例实现最优雅的方式
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201108231259986.jpeg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjI4ODIxOQ==,size_16,color_FFFFFF,t_70#pic_center)

```java
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

```



# 二、使用ProcessEngine
## 1. 编写测试类并获取


<font color=#999AAA >代码如下（示例）：



```java
package com.phoenix.workflow.service.domain;

import com.phoenix.workflow.enums.PnxProcessEngine;
import org.camunda.bpm.engine.ProcessEngine;
import org.junit.jupiter.api.Test;

/**
 * @author zerozhao
 * @version 1.0
 * @title: PnxIdentityServiceTest
 * @projectName phoenix-camunda
 * @description: TODO
 * @date 2020/11/815:33
 */
class PnxIdentityServiceTest {

    ProcessEngine processEngine = PnxProcessEngine.INSTANCE.getProcessEngine();
    @Test
    void getIdentityService() {
        System.out.println(processEngine.getIdentityService());
    }
}
```

我们的这个测试用例写得并不严谨，主要是确保我们要的实体已经生成了，我在这里使用了

> processEngine.getIdentityService()；

在下一个篇章我们将着重介绍IdentityService，这个里面将包含Camunda所有对于租户、组、用户的操作。

# 总结
ProcessEngine 是基础，可以通过xml配置或者java配置的方式来实例化ProcessEngine ，我们的样例使用了java代码的方式。

接下来我们将会开始介绍IdentityService。

关于ProcessEngine的配置，有兴趣的小伙伴也可以参考 [官方文档](https://docs.camunda.org/manual/7.13/user-guide/process-engine/process-engine-bootstrapping/)。

# Camunda流程引擎系列
<font color=#999AAA >三、租户、组、用户 - IdentityService
<hr style=" border:solid; width:100px; height:1px;" color=#000000 size=1">

</font>

<hr style=" border:solid; width:100px; height:1px;" color=#000000 size=1">

# 前言

上一个分享我们从**流程引擎配置**获得了**流程引擎**的一个对象，事实上我们去看源码，我们接下来的这些Service也都是来自于**流程引擎配置**，所以上一个分享很简短，但是很重要，在后面的分享我们也可以再回过头来把流程引擎这一块的内容再做一些更细的分享。
现在我们要开始分享IdentityService，这里面有着Camunda关于用户的主要内容，我们去理解一个系统，总是不可避免要去理解他的用户模型，因为一个系统做出来一定是解决人的问题的，所以我们对于Camunda的几个重要Service，变从用户未切入口进行分享。

<hr style=" border:solid; width:100px; height:1px;" color=#000000 size=1">


# 一、IdentityService

Identity service 是在各种租户、组、用户上的抽象，这其中由以下几个实体组成：

 - User: 一个由唯一ID确定的用户； 
 - Group: 一个由唯一ID确定的组； 
 - Membership: 用户与组的关系； 
 - Tenant:  一个由唯一ID确定的租户； 
 - Tenant Membership: 租户与组/用户的关系；

Example:

```java
User user = processEngine.getIdentityService()
  .createUserQuery()
  .userId("phoenix")
  .singleResult();
```

Camunda BPM 分为支付与可写用户实现，只读用户提供了只读的底层连接到用户与组数据库，可写用户则提供了对用户的增删改查。

为了提供定制化的身份实现，可以实现以下两个接口：


> org.camunda.bpm.engine.impl.identity.ReadOnlyIdentityProvider
> org.camunda.bpm.engine.impl.identity.WritableIdentityProvider


# 二、基于数据库的IdentityService
## 1. 简介
基于数据库的 identity service 通过从流程引擎配置的数据库进行用户相关的管理，这也是默认的identity service实现。

基于数据库的 identity service 默认同时实现了只读以及可写的关于用户、组以及相关关系管理的增删改查。

## 2. 数据库表
我们先来看看这里涉及到哪些底层数据库表
### 组表
这是Camunda关于组的表，里面字段并不多，为了更好兼容中文及特殊字符，可以将utf8修改为utf8mb4。
```sql
create table ACT_ID_GROUP (
    ID_ varchar(64),
    REV_ integer,
    NAME_ varchar(255),
    TYPE_ varchar(255),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;
```
### 用户与组关系表

```sql
create table ACT_ID_MEMBERSHIP (
    USER_ID_ varchar(64),
    GROUP_ID_ varchar(64),
    primary key (USER_ID_, GROUP_ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;
```
### 用户表

```sql
create table ACT_ID_USER (
    ID_ varchar(64),
    REV_ integer,
    FIRST_ varchar(255),
    LAST_ varchar(255),
    EMAIL_ varchar(255),
    PWD_ varchar(255),
    SALT_ varchar(255),
    LOCK_EXP_TIME_ datetime(3) NULL,
    ATTEMPTS_ integer,
    PICTURE_ID_ varchar(64),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;
```
### 用户信息表
这张表是用户补充信息表，但是官方已经不建议使用，目前留着应该是为了兼容老版本，为了以后使用不受升级可能造成的麻烦，尽量不要使用了。

```sql
create table ACT_ID_INFO (
    ID_ varchar(64),
    REV_ integer,
    USER_ID_ varchar(64),
    TYPE_ varchar(64),
    KEY_ varchar(255),
    VALUE_ varchar(255),
    PASSWORD_ LONGBLOB,
    PARENT_ID_ varchar(255),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;
```
### 租户表

```sql
create table ACT_ID_TENANT (
    ID_ varchar(64),
    REV_ integer,
    NAME_ varchar(255),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;
```
### 租户与组/用户关系表

```sql
create table ACT_ID_TENANT_MEMBER (
    ID_ varchar(64) not null,
    TENANT_ID_ varchar(64) not null,
    USER_ID_ varchar(64),
    GROUP_ID_ varchar(64),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;
```
### 表外键
在Camunda的数据库中，大量使用了外键，不确定是不是因为历史原因保留下来的，我们没有去考究这个问题，这确实不是现如今最常使用的一种手法了。

这些外键关联我们需要去注意，特别是如果我们想要直接操作数据库数据时，忽略这些外键的存在可能会导致我们的系统出错。

```sql
alter table ACT_ID_MEMBERSHIP
    add constraint ACT_FK_MEMB_GROUP
    foreign key (GROUP_ID_)
    references ACT_ID_GROUP (ID_);

alter table ACT_ID_MEMBERSHIP
    add constraint ACT_FK_MEMB_USER
    foreign key (USER_ID_)
    references ACT_ID_USER (ID_);

alter table ACT_ID_TENANT_MEMBER
    add constraint ACT_UNIQ_TENANT_MEMB_USER
    unique (TENANT_ID_, USER_ID_);

alter table ACT_ID_TENANT_MEMBER
    add constraint ACT_UNIQ_TENANT_MEMB_GROUP
    unique (TENANT_ID_, GROUP_ID_);

alter table ACT_ID_TENANT_MEMBER
    add constraint ACT_FK_TENANT_MEMB
    foreign key (TENANT_ID_)
    references ACT_ID_TENANT (ID_);

alter table ACT_ID_TENANT_MEMBER
    add constraint ACT_FK_TENANT_MEMB_USER
    foreign key (USER_ID_)
    references ACT_ID_USER (ID_);

alter table ACT_ID_TENANT_MEMBER
    add constraint ACT_FK_TENANT_MEMB_GROUP
    foreign key (GROUP_ID_)
    references ACT_ID_GROUP (ID_);
```
<hr style=" border:solid; width:100px; height:1px;" color=#000000 size=1">

## 用户相关操作
用户是组与租户的基础，所以我们先来讲讲用户，我们将通过Camunda的API对用户进行增删改查的操作，如果我们要对用户做一些自身业务特有的扩展，也建议不要修改其原本的表结构，可以封装自己的DomainService层或BizService层，添加新表进行扩展，一旦我们修改了原表，未来进行版本升级就会变得非常困难了。

### 新增用户

```java
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
```

这里面有两个关键点：

 - 我们创建了一个新的UserEntity对象
 - 使用identityService的saveUser方法保存用户
关于密码，Camunda做校验时默认会要求我们做加密，在上例中pwd存入数据库时会进行加密。如果我们要做单点登录的话，也可以集成LDAP，官方文档有做相关介绍。

### 查询用户
关于用户的查询Camunda提供了基于每个字段的方法，我们在这里只举例一个根据ID查询，小伙伴使用时可以根据提示看到还有哪些条件可以传入。

```java
    /**
     * 根据id获取用户
     * @param id
     * @return
     */
    public User getUserById(String id) {
        User user = identityService.createUserQuery().userId(id).singleResult();
        return user;
    }
```
这里我直接返回了User，那么为了更灵活的进行业务逻辑的处理，可以像下面这段例子一样，先获取到UserQuery，再基于UserQuery去添加条件。

### 获取用户列表
```java
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
```

### 删除用户

```java
    /**
     * 根据用户id删除用户
     * @param userId
     */
    public void delUser(String userId) {
        identityService.deleteUser(userId);
    }
```

### 获取租户下的用户

```java
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
```
以上会是常用的与用户相关的操作，了解上面的操作后根据ide工具的自动提示，各位应该就能举一反三去根据自己的具体业务情况，进行Camunda用户的相关操作了。
## 组相关操作
组在很多的系统中可能类似于角色，就是我们把一组具有相同特征的人放在一起，便于我们对一类人的操作与管理，以及为这一类人分配任务等。
对组的操作，依然依赖于IdentityService，我们依然列举几个简单的例子来展示对组的一些基本操作。
### 添加组

```java
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
```
与用户需要用到UserEntity类似，进行组的操作时，我们需要用到GroupEntity这个类。
### 将用户添加到组
通过建立用户与组的关系，可以将用户添加到组。

```java
    /**
     * 将用户添加到组
     * @param userId
     * @param groupId
     */
    public void createMembership(String userId, String groupId) {
        identityService.createMembership(userId, groupId);
    }
```
### 获取组列表

```java
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
```
### 删除组

```java
    /**
     * 根据组ID删除组
     * 可先获取组列表，再根据获取的ID删除
     * @param groupId
     */
    public void delGroup(String groupId) {
        identityService.deleteGroup(groupId);
    }
```
### 从组中删除用户

```java
    /**
     * 将用户从组中删除
     * @param userId
     * @param groupId
     */
    public void delMembership(String userId, String groupId) {
        identityService.deleteMembership(userId, groupId);
    }
```
### 获取租户下的组

```java
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
```

## 租户相关操作
租户的操作与用户、组会比较类似，主要区别就在于实体类是TenantEntity，我们也做一些简单的例子帮助大家知道租户相关的基本操作，以此对相关API有所了解。

```java
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
```

# 总结
在这里，我们对用户、组、租户相关的表与API做了一些基本的分享，通过这些分享，我们已经基本可以满足业务逻辑对于用户、组、租户的基本操作要求。
我们通过BizService层对DomainService层的包装，来满足我们对于这些基础要素使用的需求。
