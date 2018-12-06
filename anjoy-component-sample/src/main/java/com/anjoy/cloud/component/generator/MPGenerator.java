package com.anjoy.cloud.component.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

/**
 *
 * @author wuhy
 * @date 2018/8/14
 */
public class MPGenerator {

    @Test
    public void generateCode() {
        String packageName = "com.anjoy.cloud.component";
        boolean serviceNameStartWithI = false;//user -> UserService, 设置成true: user -> IUserService
        generateByTables(serviceNameStartWithI, packageName, "buyer","seller");
    }

    private void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
        GlobalConfig config = new GlobalConfig();
        String dbUrl = "jdbc:mysql://172.17.0.80:33016/anjoys2b_deprecated?autoReconnect=true&failOverReadOnly=false&useUnicode=true&characterEncoding=utf8&allowMultiQueries=true";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig
                .setUrl(dbUrl)
                .setUsername("root")
                .setPassword("Mysql@5.7")
                .setDriverName("com.mysql.jdbc.Driver");
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setSuperControllerClass("com.anjoy.cloud.component.controller.base.BaseController")
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setInclude(tableNames)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true);//修改替换成你需要的表名，多个表名传数组
        config.setActiveRecord(false)
                .setSwagger2(true)
                .setAuthor("")
                .setOutputDir("d:\\codeGen")
                .setFileOverride(true)
                .setEnableCache(false)
                .setBaseResultMap(false)
                .setBaseColumnList(false)
                .setMapperName("%sDao")
                .setXmlName("%sDao");
        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setController("controller.module")
                                .setEntity("entity")
                                .setMapper("dao")
                                .setXml("dao/impl")
                ).execute();
    }

    private void generateByTables(String packageName, String... tableNames) {
        generateByTables(true, packageName, tableNames);
    }
}