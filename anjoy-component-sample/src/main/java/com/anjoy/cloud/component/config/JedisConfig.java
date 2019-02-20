package com.anjoy.cloud.component.config;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

    @Value("${redis.sentinel.masterName:#{null}}")
    private String sentinelMasterName;

    @Value("${redis.sentinel.servers:#{null}}")
    private String sentinelServers;


    //    @Bean
    public JedisPoolConfig getjedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(300);
        jedisPoolConfig.setMaxTotal(60000);
        jedisPoolConfig.setTestOnBorrow(true);
        return jedisPoolConfig;
    }

    private String getRedisType(){
        if (StringUtils.isBlank(sentinelMasterName)){
            return "single";
        }else{
            return "sentinel";
        }
    }

    @Bean
    public JedisPool getJedisPool() {
        if (getRedisType().equals("single")) {
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
        } else {
            return null;
        }
    }

    @Bean
    public JedisSentinelPool getJedisSentinelPool() {
        if (getRedisType().equals("sentinel")) {
            JedisPoolConfig jedisPoolConfig = getjedisPoolConfig();
            JedisSentinelPool jedisSentinelPool;

            Set<String> sentinelServerSet = new HashSet<String>(Arrays.asList(sentinelServers.split(",")));

            jedisSentinelPool = new JedisSentinelPool(
                    sentinelMasterName,
                    sentinelServerSet,
                    jedisPoolConfig,
                    Integer.valueOf(connectionTimeout),
                    password,
                    Integer.valueOf(database)
            );
            log.debug("jedis-sentinel初始化完毕");
            return jedisSentinelPool;
        }else{
            return null;
        }
    }


}
