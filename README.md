#安井微服务开发平台1.0版
----------------------------------
### 学习前提：
java web开发相关经验
springMVC框架开发经验，熟悉spring分层的意义以及对应代码的编写方式（controller，service，dao，entity，vo）
Mybatis开发经验，熟悉xml编写sql的相关经验
mysql数据库开发经验，熟悉表，字段，sql，数据库事务等
### 用法：
使用本框架需要使用idea2017以上版本
idea必须下载lombok插件，可以参考https://jingyan.baidu.com/article/0a52e3f4e53ca1bf63ed725c.html
直接在项目上开自己的分支，并复制anjoy-component-sample为自己的项目，开始撸代码。或者你要fork整个项目到自己的repo中去也行。**绝对禁止直接在master主干上撸自己的代码或者将自己的代码合并到主干，违者删库踢出**

mybatis-plus的用法可以参考SellerController或者BuyerController或者直接看官网：
https://mybatis.plus/guide/crud-interface.html

lombok语法，，，这个实在没什么好说的请自行百度

### 编译方式：

mvn clean package -e 编译后为jar文件

### 调试方式：

idea能直接认对应的application，可以直接运行，由于springboot的热部署中间件devtools有bug会导致加载类在判断equal或者是存入redis后取出做类型强转换时出错，所以本项目未内置热部署插件

### 运行方式：

运行使用命令行 java -jar XXX.jar
可以把application.properties中的参数携带到命令行中，达到动态配置的目的，例如：

java -jar anjoy-component-sample-1.0.jar --server.port=8980 --logback.home=/testlog/anjoy --spring.datasource.druid.url="jdbc:mysql://172.17.0.80:33016/anjoys2b_deprecated?autoReconnect=true&failOverReadOnly=false&useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&useSSL=false" --spring.datasource.druid.username=root --spring.datasource.druid.password="Mysql@5.7" --redis.host=172.17.0.80 --redis.port=6379 --redis.password="Sumpo,123" --redis.database=0 

上面这段的意思就是动态配置服务器端口，日志文件位置，数据库连接参数与redis连接参数，其他也是类似的

### 特性列表：

本框架适用于各类微服务开发（CRUD项目，授权服务，分布式计算，接口调用等等），既可以作为大型项目构建的基础微服务组件使用，也可以作为独立的前后端分离中小项目后端使用

编译后的微服务可以独立使用，也可以接入eureka生态实现负载均衡，热部署与灰度发布，接入后支持健康度检查与监控等等。

默认启用了swagger，并且swagger 注入的地址已经改变，比如你的服务启动是在 http://localhost:8088 ，那么对应swagger地址为 http://localhost:8088/anjoyCloudApiDoc/swagger-ui.html

支持feign+ribbon+eureka的微服务调用方式，支持负载均衡，降级，熔断，如果需要和eureka联合使用，请查考冻品先生分支的调用方式

支持统一token鉴权与用户授权微服务分离，详情请参考冻品先生分支

全局日志切面已经将Rest调用时的输入参数与controller处理后的输出结果统一写入日志中，级别为debug，请注意查看

时间类型默认使用jdk8的localdatatime替换掉默认的date和datetime（使用mybatis-plus自动生成的entity）

默认序列化和反序列化引擎为阿里的fastjson，非原生jackson。主要是考虑到前端广泛格式的兼容度和调用量级高的效率，支持直接在切面将字符串格式的日期反序列化成后台的localdatetime，后台的localdatetime序列化输出到前端时支持时间戳，字符串日期时间与ISO8601等3种输出格式，可以自由配置。（喷子走开，我就是fastjson死忠你们能拿我怎么样-_-）

支持springcloud的计数器接口，可以联合prometheus+grafana实现微服务监控，实现效果在这里：http://172.17.0.53:3000 用户：admin，密码：admin，只需要配置prometheus+grafana获取计数器接口即可使用，详情请参考冻品先生项目或者直接百度

日志可以直接输出到rocketMQ，支持输出级别调整，默认为不输出，可以参考logback-spring.xml中的相关代码，考虑到这个不是刚需功能，代码暂时注释掉

作者：吴宏宇

-----------------------------------
架构说明:
主体架构为：spring-cloud（Finchley.SR2） + sprint-boot（2.06） + spring + Mybatis-plus（3.06） + redis(jedis2.9) + druid + swagger(2.7) + openfeign(Finchley整合) + rocketMQ(4.2) + lombok