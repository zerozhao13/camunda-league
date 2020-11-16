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
    private final ProcessEngine processEngine;
    PnxProcessEngine () {
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

# Camunda流程引擎系列
四、运行时服务类 - RuntimeService
<hr style=" border:solid; width:100px; height:1px;" color=#000000 size=1">

</font>

<hr style=" border:solid; width:100px; height:1px;" color=#000000 size=1">

# 前言

该服务类包含了许多与实例相关的服务，流程定义好的规则开始运转时，就是我们本次分享的主要内容。

<hr style=" border:solid; width:100px; height:1px;" color=#000000 size=1">


# 一、RuntimeServiceImpl


我们对运行时服务的调用，基本都可以基于该类进行操作，官方提供了非常多的方法，有兴趣的小伙伴可以搜索 RuntimeServiceImpl 看看源码，在org.camunda.bpm.engine.impl包下。

## 派生类

 - 可根据不同API启动实例，并返回ProcessInstance对象；
 - 可异步删除所有实例，并返回Batch对象；
 - 可获取变量信息，并返回VariableMap对象；
 - 可获取变量信息，并返回Map<String, Object>对象；
 - 可获取单个变量信息，并返回TypedValue；
 - 可查询执行实例，并返回ExecutionQuery对象；
 - 可自定义SQL查询并执行实例，并返回NativeExecutionQuery对象；
 - 可查询流程实例信息，并返回ProcessInstanceQuery对象；
 - 可自定义SQL查询流程实例信息，并返回NativeProcessInstanceQuery对象；
 - 可查询Incident相关信息，并返回Incident对象；

## 启动实例方式

 - processDefinitionKey
 - processDefinitionKey + 租户ID
 - messageName + 租户ID
 - messageName
 - processDefinitionId

# 二、流程定义
## 1.定义

> 流程定义：通过资源文件，获取已事先定义好的流程规则信息。
> 执行实例：发起流程定义，可以认为流程定义就像定义了一个类，而执行实例就是生成了一个对象。
> 流程实例：一个流程的最长分支，就好比项目管理里面的关键路径。

```java
public interface ProcessInstance extends Execution
```

启动流程时会首先创建流程实例，如果存在子流程、多实例、并行网关，则会再次创建执行实例。流程运转的过程中永远执行的时自己对应的执行实例。当所有执行实例按照规则执行完毕之后，则流程实例随之结束。

Camunda用ProcessInstance对象去描述流程执行的每一个节点，流程按照流程定义的规则执行一次的过程，就可以表示执行对象Execution。

一个流程中，执行对象可以是多个，执行实例在某个活动节点时，需要我们通过特定API去完成，然后执行实例继续往下走。

一个实例运行的简单过程：

 1. 启动实例；
 2. 创建实例；
 3. 更新实例；
 4. 完成实例。

稍微复杂一点的如存在并行网关的话，流程实例就是这样的：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201114183817321.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjI4ODIxOQ==,size_16,color_FFFFFF,t_70#pic_center)

## 2. 启动流程

RuntimeService有很多启动流程的API，我们演示其中一种，因为不同的API主要差别在于传参的不同，传参不同主要是业务逻辑的不同，但是其本质大同小异。

### 获取runtimeService

```java
private RuntimeService runtimeService = PnxProcessEngine.INSTANCE.getProcessEngine().getRuntimeService();
```
### 根据key启动实例
这个KEY可以在数据库表 ACT_RE_PROCDEF 的KEY_字段查看到，当然也可以在流程文件中查到，或者通过查询接口查到。

```java
    public ProcessInstance startProcess(String key) {
        return runtimeService.startProcessInstanceByKey(key);
    }
```
实例启动后，程序会返回ProcessInstance 的对象。
下面是我们的测试代码，可以看看返回的对象包含什么样的数据

```java
    @Test
    void startProcess() {
        String key = "Process_0iplu3k";
        ProcessInstance processInstance = pnxRuntimeService.startProcess(key);
        processInstance.getId();
        System.out.println("id is : " + processInstance.getId());
        System.out.println("getBusinessKey is : " + processInstance.getBusinessKey());
        System.out.println("getRootProcessInstanceId is : " + processInstance.getRootProcessInstanceId());
        System.out.println("getProcessInstanceId is : " + processInstance.getProcessInstanceId());
    }
```

当流程实例启动时，以下两张表会有新的数据插入：

> ACT_RU_EXECUTION
> ACT_RU_TASK

同时会有几张历史表也会插入数据（ACT_HI_XXXX都是历史表）：

> ACT_HI_ACTINST
> ACT_HI_TASKINST
> ACT_HI_PROCINST
> ACT_HI_IDENTITYLINK

<hr style=" border:solid; width:100px; height:1px;" color=#000000 size=1">

### 完成Task
一个实例启动过后，对应的任务需要完成才会走入下一个节点，这时候我们会有大量对Task的操作，从产品的角度来看，一个任务启动会根据规则分配到人，然后该用户获取自己的任务列表，进行操作后完成该任务。

那么我们看看这几个操作会涉及哪几个API，以及用测试代码做一下测试看看，然后对完成一个任务时会操作哪些数据做一些讲解。

```java
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
}
```
<hr>
测试代码：

```java
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
```
在这组单元测试代码中，我们会先查询任务列表，然后对该任务赋予一个执行人，接下来会完成该任务，用原任务号去查询，该任务已经没有了。
那么原任务去哪里了呢？进入历史表了，而任务表会插入一个新的任务，是我们流程定义的下一个节点。
任务的完成会涉及到多张表的数据操作，那么我们来看看分别是那几张表，这将有利于我们理解其背后的逻辑，从而更好地完成我们的业务诉求。

插入：

> ACT_HI_TASKINST
> ACT_HI_IDENTITYLINK(不一定有，要看用户模式)
> ACT_HI_ACTINST
> ACT_RU_TASK

删除：

> ACT_RU_TASK

更新：

> ACT_RU_EXECUTION
> ACT_HI_ACTINST
> ACT_HI_TASKINST

### 历史数据
在流程引擎中，更多的数据会记录进历史表中，我们要查询一些任务的状态或者列表，都会使用到这些历史表，而有的数据会在系统运行中，同步插入历史表与当前任务表。
我们来看看几张主要的历史表以及与之对应的当前数据表。

> - ACT_RU_EXECUTION：正在执行的信息
> - ACT_HI_PROCINST：已经执行完的历史流程实例信息
> - ACT_HI_ACTINST：所有完成的历史活动
> - ACT_RU_TASK：正在执行的任务数据
> - ACT_HI_TASKINST：已经执行完的历史任务数据

### 设定发起人与使用initiator
之前的流程中，我们设置了流程的当前节点执行人，但是在实际的场景中我们的流程是不是还会有一个发起人？以及每个任务节点的指派人？指派人的设定会有一点不同，并且可以将指派人作为变量传入后续的任务中。

```java
    @Autowired
    PnxIdentityService pnxIdentityService;
    @Autowired
    PnxRuntimeServiceImpl pnxRuntimeService;

    /**
     * 指定启动用户的开启流程
     * @param authUser
     * @param processKey
     */
    public void startProcess(String authUser, String processKey) {
        pnxIdentityService.setAuthUser(authUser);
        pnxRuntimeService.startProcess(processKey);
    }
```
通过IdentityService来设置流程发起人。

**NOTE：**我们开启流程最好用key，不要使用id，id是系统标识，会伴随部署更新对应的值，导致系统可能走到我们预期以外的流程上去。

### 删除流程实例
一个流程，如果走到一半，或者别的原因，我们希望中止了，那么就可以使用流程删除，流程删除的操作也很简单，调用RuntimeService的API即可，传入流程实例的ID与删除原因。

```java
    public void delProcess (String processId, String reason) {
        runtimeService.deleteProcessInstance(processId, reason);
    }
```
这个过程中会涉及到如下数据库表操作：
删除：

> - ACT_RU_TASK
> - ACT_RU_EXECUTION

更新：

> - ACT_HI_ACTINST
> - ACT_HI_PROCINST
> - ACT_HI_TASKINST

### 流程的挂起与激活
有时候我们不希望一个流程被删除，但是也不希望再被操作，等待以后重新恢复。
当一个流程实例挂起时，该实例下的子流程也都会挂起，所以需要慎重确认。

 - 流程定义的挂起

```java
    /**
     * 挂起流程定义，挂起后基于该定义的流程将不能再执行，除非先激活
     *
     * @param id
     */
    public void suspendProcessDef(String id) {
        runtimeService.suspendProcessInstanceByProcessDefinitionId(id);
    }
```

- 流程实例的挂起

```java
    /**
     * 挂起流程定义，挂起后基于该定义的流程将不能再执行，除非先激活
     *
     * 两种挂起方式
     *
     * @param id
     */
    public void suspendProcessDef(String id) {
        //runtimeService.suspendProcessInstanceByProcessDefinitionId(id);
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        processInstanceQuery.processInstanceId(id);
        runtimeService.updateProcessInstanceSuspensionState().byProcessInstanceQuery(processInstanceQuery).suspendAsync();
    }
```

- 激活流程定义

```java
    /**
     * 两种激活流程定义的方法
     * @param id
     */
    public void activeProcessDef(String id) {
        //repositoryService.activateProcessDefinitionById(id);
        repositoryService
                .updateProcessDefinitionSuspensionState()
                .byProcessDefinitionId(id)
                .includeProcessInstances(true)
                .activate();
    }
```

# 总结

# Camunda流程引擎系列
五、节点
<hr style=" border:solid; width:100px; height:1px;" color=#000000 size=1">

</font>

<hr style=" border:solid; width:100px; height:1px;" color=#000000 size=1">

# 前言

流程引擎的关键，特别是基于BPMN的流程引擎，我们应该去了解它包含哪些元素。

BPMN基本元素包括了这些元素，如下：

- 泳道：池和道；
- 三种连接对象：序列流、消息流和关联；
 - 一种活动：任务（Task），其中包括了子流程任务（Sub-ProcessTask）；
 - 两种事件：普通开始事件（NoneStart Event）和普通结束事件（None End Event）；
 - 两种网关：排他网关（Exclusive）和并发网关（Parallel）；
 - 一种人工制造物：附注。 

我们在这次分享中的节点，会是一个比较综合的东西，因为我们看基本元素里面并没有元素叫这个名字。我们会在这里介绍到一些常用的节点，包括启动、结束以及一些task，虽然我们在前面的分享已经涉及到了一些。

# 一、开始节点

 - 开始节点代表一个流程的开始；
 - 一个流程文件中，开始节点只能有一个，不能有多个；
 - 子流程与引用流程其开始节点也只能是一个；
 - 启动流程实例的时候，从开始节点开始运转流程；

开始节点有几种类型，如图：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201116205202726.png#pic_center)

# 结束节点

 - 结束节点代表一个流程的结束；
 - 在一个流程文件中，结束节点可以有多个；
 - 如果实例运转到结束节点，则表示当前执行实例结束了；
结束节点有几种类型，如图：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201116205654853.png#pic_center)


# 二、UserTask
用户任务属于等待节点，既表示系统走到这里会等待人来处理，不会自己往后走，那么对于用户任务，我们可以先想想这几个问题：

 - 任务从何而来？
 - 任务由谁处理？
 - 任务如何处理？
 - 办理过的任务如何查看？
### TaskService
这是Camunda的任务服务类，我们在之前runtimeService的分享中，其实已经涉及到了，我们可以从TaskService中发现各种执行任务相关的API。

我们在这里再分享一下通过变量的方式来启动任务：

 1. 在配置流程时，配入变量，如assignee一栏，将其配置为 #{userId}；
 2. 启动流程或任务时通过variables参数传入变量的键值对；

```java
    /**
     * 可通过定义的变量传入来启动对应流程
     * 如在流程中定义了变量，如果不在启动时传入，系统会报错
     * 如流程定义中指定用户为 #{userId}, 那么在启动时我们需要通过vars传入userId，放入map中
     *
     * @param key
     * @param vars
     * @return
     */
    public ProcessInstance startProcess(String key, Map<String, Object> vars) {
        return runtimeService.startProcessInstanceByKey(key, vars);
    }
```
## 监听器
监听器类似于切片，可以在执行实例或任务的特定节点触发特定事件。
任务监听器是任务节点所特有的，而执行监听器是所有节点都具有的。监听器有特定的事件，我们需要在配置中为该类型指定监听器对应的类。

 1. 在配置中配置监听器
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201116232938798.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjI4ODIxOQ==,size_16,color_FFFFFF,t_70#pic_center)
2. 在代码中编写监听器

```java
/**
 * @author zerozhao
 * @version 1.0
 * @title: PnxTaskListenerImpl
 * @projectName phoenix-camunda
 * @description: TODO 我们可以通过监听器，在这里获取下一个任务的指派人，并将其设置进去。
 * @date 2020/11/1623:08
 */
public class PnxTaskListenerImpl implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        String eventName = delegateTask.getEventName();
        int priority = delegateTask.getPriority();
        if ("create".equals(eventName)) {
            delegateTask.setAssignee("user");
        }
    }
}
```

## 动态添加任务处理人

# 组任务
## 设置组任务处理人

# 角色组

# 总结

