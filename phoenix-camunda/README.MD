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
