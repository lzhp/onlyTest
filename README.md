主要放一些试验性质的项目


# 主要技术及工具

1. JDK 1.8+
2. maven 3.5 + springboot2 + jpa(mybatis) + junit4 + sonarLint
3. google guava (依赖包)

spring boot 2

```
<parent>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-parent</artifactId>
	<version>2.0.0.RELEASE</version>
	<relativePath /> <!-- lookup parent from repository -->
</parent>
```
	
junit4不需要直接引用，直接引用spring-boot-starter-test即可
	
```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-test</artifactId>
</dependency>
```

guava，版本

```
		<dependency>
			<groupId>com.google.guava</groupId>
			<artifactId>guava</artifactId>
			<version>24.0-jre</version>
		</dependency>
```

# 环境说明
eclipse或ideal, 研发网地址：[\\file.rd.domain.com\software\develop\IDE\](file:\\file.rd.domain.com\software\develop\IDE\)

1. 安装 `lombok` 插件(直接双击lombok.jar)
2. 代码风格 `google java style`，文件eclipse-java-google-style.xml，开发工具安装google代码风格操作如下

   eclipse:  select Java/Code Style/Formatter. Import the settings file by selecting Import
  
   idea: Copy it into your config/codestyles folder in your IntelliJ settings folder. Under Settings -> Editor -> Code Style select the google-styleguide as current code style for the Metanome project.

3. maven，研发网maven仓库地址： [http://maven-repo.rd.domain.com:8081/nexus/](http://maven-repo.rd.domain.com:8081/nexus/)，或者直接拷贝` \\file.rd.domain.com\software\develop\conf\.m2\settings.xml`覆盖当前用户的settings.xml
4. 空白后端的开发模板

# 注意事项
1. 代码风格, google java style
2. 层次关系：controller->service->repository，**不允许** 逆向或跨层调用
3. 业务逻辑应在service层进行封装，对于写数据库等操作，应在service层 **启用事务**。


# 编码规范
1. 接口及实现的风格，接口 `Bussiness`，实现 `BussinessImpl`，或者不定义接口，只将实现放在在`Bussiness`中
2. 日期类型，使用`java8`的`LocalDate LocalDateTime`等进行定义，不允许使用`java.util.Date`类
3. 公共函数，首选JDK自带，在JDK不能满足要求的情况下，首选google guava库里的各种函数，最后才考虑自行封装公共函数
4. log统一使用`Slf4j`，在使用lombok的情况下，在类的前面加`@Slf4j`注解，然后在类的函数里直接使用如下方式记录日志：

```
	log.trace(……)
	log.debug(……)
	log.info(……)
	log.error(……)
	log.fatal(……)
```

5. 错误处理：	
 
   - 在底层只处理自己应该处理的错误，对于已知的业务异常情况，应该抛出自定义的业务异常，异常应包含错误号、关键业务单证号、简单错误描述等信息，对于有嵌套异常的情况，应该讲底层的异常包装上述内容后向上层抛出。底层异常不记录log日志（否则会重复记录log）。
   - Service层应处理各种异常情况，保证业务调用方能得到足够的异常信息，同时，在Service层应记录log信息，在系统上线后能通过service层的log信息排查错误。
  -  Controller提供统一的异常处理机制，因此一般情况下不需要处理异常。


# 工作步骤
1. 拷贝模板目录 spring-template
2. 修改pom文件中的 `groupId artifactId name description`等属性 

```
	<groupId>cn.customs.H2018.example</groupId>
	<artifactId>demo-boot2</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>
	<name>demo-boot2</name>
	<description>Demo project for Spring Boot</description>
```

3. 将项目导入到开发工具中，可以工作了

# 项目目录结构

方法一：

```
root
│  pom.xml
│  README.md
├─src
│  ├─main
│  │  ├─java
│  │  │  └─cn.customs.h2018.project
│  │  │                  │  Application.java                根目录
│  │  │                  ├─business1                        业务1目录
│  │  │                  │  │  Bussiness1.java                业务的pojo
│  │  │                  │  │  Bussiness1Controller.java      业务的controller
│  │  │                  │  │  Bussiness1Repository.java      业务的数据库封装
│  │  │                  │  │  Bussiness1Service.java         业务的逻辑接口定义（可选）
│  │  │                  │  │  Bussiness1ServiceImpl.java     业务的逻辑实现
│  │  │                  │  └─config                          业务的自身配置信息
│  │  │                  ├─business2                        业务2目录
│  │  │                  │  │  Bussiness2.java                业务的pojo
│  │  │                  │  │  Bussiness2Controller.java      业务的controller
│  │  │                  │  │  Bussiness2Repository.java      业务的数据库封装
│  │  │                  │  │  Bussiness2Service.java         业务的逻辑封装
│  │  │                  │  └─config                          业务的自身配置信息
│  │  │                  ├─config                            整个应用的配置信息目录
│  │  │                  │      ProjectConfiguration.java     应用的配置信息
│  │  │                  └─utils                            整个应用公用的工具类
│  │  │                        CustomBussinessRuntimeException.java 自定义业务错误
│  │  └─resources                                           资源目录
│  │      │  application.yml                                  应用的配置，建议使用yaml文件
│  │      ├─static                                            静态资源
│  │      └─templates                                         模板等
└─ └─test                                                    单元测试相关
      └─java
          └─cn.customs.h2018.example
                          │  ApplicationTests.java            主程序测试
                          ├─bussiness1                        业务1
                          │      Bussiness1RepositoryTests.java  数据库访问单元测试
                          │      Bussiness1ServiceTests.java     业务逻辑单元测试
                          │      Bussiness1Tests.java            pojo单元测试
                          ├─bussiness2                       业务2
                          │      Bussiness2RepositoryTests.java 
                          │      Bussiness2ServiceTests.java
                          │      Bussiness2Tests.java
                          └─utils                             工具类测试
                                                           
```


方式二：

```
root
|   pom.xml
|
+---src
|   +---main
|   |   +---java
|   |   |   \---cn.customs.h2018.sample
|   |   |                   |   Application.java                           主程序
|   |   |                   |   ApplicationConfig.java                     配置
|   |   |                   +---controller                                 Controller
|   |   |                   |       BussinessController.java                 业务controller 
|   |   |                   +---dao                                        Dao数据库访问
|   |   |                   |       BussinessRepository.java                 jpa方式访问，其他方式（mybatis）也放这个包内 
|   |   |                   +---pojo                                       pojo实体
|   |   |                   |       Bussiness.java                           业务实体定义   
|   |   |                   +---service                                    服务
|   |   |                   |       BussinessService.java                    业务服务类
|   |   |                   \---utils                                      工具类
|   |   |                           CustomBussinessRuntimeException.java     自定义业务错误  
|   |   \---resources                                                      资源目录
|   |       |   application.yml                                              配置文件，推荐yaml
|   |       |   logback-spring.xml                                           日志配置文件
|   |       +---static                                                       静态资源
|   |       \---templates                                                    模板
\-  \---test                                                                 测试代码
       \---java
           \---cn.customs.h2018.sample
                           |   TemplateApplicationTests.java                主程序测试相关
                           +---controller
                           |       BussinessControllerTests.java            业务controller测试
                           \---service  
                                   BussinessServiceTests.java               业务service类测试
```

# 附录
亚马逊军规：
1. 所有的团队都要以服务接口的方式，提供数据和各种功能。
2. 团队之间必须通过接口来通信。
3. 不允许任何其他形式的互操作：不允许直接链接，不允许直接读其他团队的数据，不允许共享内存，不允许任何形式的后门。唯一许可的通信方式，就是通过网络调用服务。
4. 具体的实现技术不做规定，HTTP、Corba、PubSub、自定义协议皆可。
5. 所有的服务接口，必须从一开始就以可以公开作为设计导向，没有例外。这就是说，在设计接口的时候，就默认这个接口可以对外部人员开放，没有讨价还价的余地。
