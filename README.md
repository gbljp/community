#安井springcloud组件平台1.0版
----------------------------------
用法：
1. 使用本框架需要使用idea
2. idea必须下载lombok插件
3. 复制下面的anjoy-component-sample项目新增为自己的项目即可
在主pom中增加对自己项目的引用
或
复制整个项目作为自己的项目，然后修改包名

mybatis-plus的用法可以参考SellerController或者BuyerController或者直接看官网：https://mp.baomidou.com/guide/#%E7%89%B9%E6%80%A7

lombok语法，，，这个实在没什么好说的请自行百度

编译方式：
mvn clean package -e 编译后为jar文件，运行使用命令行 java -jar XXX.jar

作者：吴宏宇

-----------------------------------
架构说明:
主体架构为：spring-cloud（Finchley.SR2） + sprint-boot（2.06） + spring + Mybatis-plus（3.06） + redis(jedis2.9) + druid + swagger(2.0) + openfeign(Finchley整合) + rocketMQ(4.2)