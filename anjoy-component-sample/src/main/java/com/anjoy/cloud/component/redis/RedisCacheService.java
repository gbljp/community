package com.anjoy.cloud.component.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/*
 *
 * redis 缓存，使用方式是直接注入后，putobject或者getobject
 *
 * */

@Service
public class RedisCacheService {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 获取Jedis对象
     * @return
     */
    public synchronized  Jedis getJedis() {
        Jedis jedis = null;
        if(jedisPool != null){
            try{
                if(jedis == null ){
                    jedis = jedisPool.getResource();
                }
            }catch(Exception ex){
                returnBrokenResource(jedis);
                throw ex;
            }
        }
        return jedis;
    }

    /**
     * 回收Jedis对象资源
     * @param jedis
     */
    public synchronized  void returnResource(Jedis  jedis) {
        if(jedis != null){
            jedis.close();
        }
    }

    /**
     * Jedis对象出异常的时候，回收Jedis对象资源
     * @param jedis
     */
    public synchronized  void returnBrokenResource(Jedis  jedis) {
        if(jedis != null){
            jedis.close();
        }

    }

    //放入对象
    public void putObject(String key, Object object) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key.getBytes(), SerializationUtils.serialize(object));
        } catch (Exception ex) {
            returnBrokenResource(jedis);
            throw ex;
        } finally {
            returnResource(jedis);
        }
    }

    //放入对象并设置时间
    public void putObject(String key, Object object, int expirationInSeconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.setex(key.getBytes(), expirationInSeconds, SerializationUtils.serialize(object));
        } catch (Exception ex) {
            returnBrokenResource(jedis);
            throw ex;
        } finally {
            returnResource(jedis);
        }
    }

    //获取对象
    public <T> T getObject(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return (T) SerializationUtils.deserialize(jedis.get(key.getBytes()));
        } catch (Exception ex) {
            returnBrokenResource(jedis);
            throw ex;
        } finally {
            returnResource(jedis);
        }
//		return null;

    }

    //删除对象
    public void removeObject(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.del(key.getBytes());
        } catch (Exception ex) {
            returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }
    }
    /**
     * 模糊查找并删除对象
     * key + *  模糊主键加通配符
     */
    public void fuzzySearchAndRemoveObject(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Set<String> keys = jedis.keys( key + "*");
            for (String item : keys) {
                jedis.del(item.getBytes());
            }
        } catch (Exception ex) {
            returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }
    }
    //判断key是否存在
    public boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.exists(key.getBytes());
        } catch (Exception ex) {
            returnBrokenResource(jedis);
            throw ex;
        } finally {
            returnResource(jedis);
        }
//		return false;
    }

    //设置对象过期时间（秒）
    public long expire(String key, int expirationInSeconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.expire(key.getBytes(), expirationInSeconds);
        } catch (Exception ex) {
            returnBrokenResource(jedis);
            throw ex;
        } finally {
            returnResource(jedis);
        }
//		return null;

    }

    //获取对象过期时间剩余（秒）
    public long ttl(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.ttl(key.getBytes());
        } catch (Exception ex) {
            returnBrokenResource(jedis);
            throw ex;
        } finally {
            returnResource(jedis);
        }
//		return null;

    }

    //生产环境调用下面这个方法会卡死
//    public Set<String> keys(final String pattern) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.keys(pattern);
//        } catch (Exception ex) {
//            handleException(ex, jedisPool, jedis);
//        } finally {
//            if (jedis != null && jedis.isConnected()) {
//                jedis.close();
//            }
//        }
//        return null;
//    }

//    /**
//     * 运行时异常，IO异常，销毁jedis对象
//     *
//     * @param ex
//     * @param jedisPool
//     * @param jedis
//     */
//    private void handleException(Exception ex, JedisPool jedisPool, Jedis jedis) {
//        if (jedis == null)
//            throw new NullPointerException("jedis is null, please check the redis server.");
//        if (ex instanceof IOException) {
//            jedis.close();
//        }
//    }




    //获取并且删除对象
    public Object getAndRemoveObject(String key) {
        Object resultObj = getObject(key);
        if (resultObj != null){
            removeObject(key);
            return resultObj;
        }else{
            return null;
        }
    }



}