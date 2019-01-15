package com.anjoy.cloud.component.config;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisConfig {

    private static Logger log = LoggerFactory.getLogger(JedisConfig.class);

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private String port;

    @Value("${redis.connectionTimeout}")
    private String connectionTimeout;

    @Value("${redis.password}")
    private String password;

    @Value("${redis.database}")
    private String database;


//    @Bean
    public JedisPoolConfig getjedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(300);
        jedisPoolConfig.setMaxTotal(60000);
        jedisPoolConfig.setTestOnBorrow(true);
        return jedisPoolConfig;
    }

    @Bean
    public JedisPool getJedisPool(){
        JedisPoolConfig jedisPoolConfig = getjedisPoolConfig();
        JedisPool jedisPool;
        if (StringUtils.isBlank(password)) {
            jedisPool = new JedisPool(
                    jedisPoolConfig,
                    host,
                    Integer.valueOf(port)
            );
        } else {
            jedisPool = new JedisPool(
                    jedisPoolConfig,
                    host,
                    Integer.valueOf(port),
                    Integer.valueOf(connectionTimeout),
                    password,
                    Integer.valueOf(database)
            );
        }
        log.debug("jedis初始化完毕");
        return jedisPool;
    }

}
