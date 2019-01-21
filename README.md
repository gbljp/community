#安井springcloud组件平台1.0版
----------------------------------
用法：
1. 使用本框架需要使用idea
2. idea必须下载lombok插件，可以参考https://jingyan.baidu.com/article/0a52e3f4e53ca1bf63ed725c.html
3. 直接在项目上开自己的分支，并复制anjoy-component-sample为自己的项目，开始撸代码。或者你要fork整个项目到自己的repo中去也行。**绝对禁止直接在master主干上撸自己的代码或者将自己的代码合并到主干，违者删库踢出**

mybatis-plus的用法可以参考SellerController或者BuyerController或者直接看官网：https://mp.baomidou.com/guide/#%E7%89%B9%E6%80%A7

lombok语法，，，这个实在没什么好说的请自行百度

编译方式：

mvn clean package -e 编译后为jar文件

调试方式：

idea能直接认对应的application，可以直接运行，由于springboot的热部署中间件devtools有bug会导致加载类在判断equal或者是存入redis后取出做类型强转换时出错，所以本项目未内置热部署插件

运行方式：

运行使用命令行 java -jar XXX.jar
可以把application.properties中的参数携带到命令行中，达到动态配置的目的，例如：

java -jar anjoy-component-sample-1.0.jar --server.port=8980 --logback.home=/testlog/anjoy --spring.datasource.druid.url="jdbc:mysql://172.17.0.80:33016/anjoys2b_deprecated?autoReconnect=true&failOverReadOnly=false&useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&useSSL=false" --spring.datasource.druid.username=root --spring.datasource.druid.password="Mysql@5.7" --redis.host=172.17.0.80 --redis.port=6379 --redis.password="Sumpo,123" --redis.database=0 

上面这段的意思就是动态配置服务器端口，日志文件位置，数据库连接参数与redis连接参数，其他也是类似的

swagger 注入的地址已经改变，比如你的服务启动是在 http://localhost:8088 ，那么对应swagger地址为 http://localhost:8088/anjoyCloudApiDoc/swagger-ui.html

作者：吴宏宇

-----------------------------------
架构说明:
主体架构为：spring-cloud（Finchley.SR2） + sprint-boot（2.06） + spring + Mybatis-plus（3.06） + redis(jedis2.9) + druid + swagger(2.0) + openfeign(Finchley整合) + rocketMQ(4.2) + lombok