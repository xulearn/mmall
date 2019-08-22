package com.mmall.util;

import com.mmall.common.RedisPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

@Slf4j
public class RedisPoolUtil {

    public static Long expire(String key,int exTime){ //设置key的有效期，单位秒
        Jedis jedis = null;
        Long result = null;

        try {
            jedis = RedisPool.getJedis();
            result = jedis.expire(key,exTime);
        } catch (Exception e) {
            log.error("expire key:{} error",key,e);
            RedisPool.returnBrokenResource(jedis);
            return result;  //null
        }
        RedisPool.returnResource(jedis);
        return result;  //message
    }

    public static String setEx(String key,String value,int exTime){ //exTime单位是秒
        Jedis jedis = null;
        String result = null;

        try {
            jedis = RedisPool.getJedis();
            result = jedis.setex(key,exTime,value);
        } catch (Exception e) {
            log.error("setex key:{} value:{} error",key,value,e);
            RedisPool.returnBrokenResource(jedis);
            return result;  //null
        }
        RedisPool.returnResource(jedis);
        return result;  //message
    }

    public static String set(String key,String value){
        Jedis jedis = null;
        String result = null;

        try {
            jedis = RedisPool.getJedis();
            result = jedis.set(key,value);
        } catch (Exception e) {
            log.error("set key:{} value:{} error",key,value,e);
            RedisPool.returnBrokenResource(jedis);
            return result;  //null
        }
        RedisPool.returnResource(jedis);
        return result;  //message
    }

    public static String get(String key){
        Jedis jedis = null;
        String result = null;

        try {
            jedis = RedisPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("get key:{}  error",key,e);
            RedisPool.returnBrokenResource(jedis);
            return result;  //null
        }
        RedisPool.returnResource(jedis);
        return result;  //message
    }

    public static Long del(String key){
        Jedis jedis = null;
        Long result = null;

        try {
            jedis = RedisPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("get key:{}  error",key,e);
            RedisPool.returnBrokenResource(jedis);
            return result;  //null
        }
        RedisPool.returnResource(jedis);
        return result;  //message
    }

    public static void main(String[] args) {
        Jedis jedis = RedisPool.getJedis();
        RedisPoolUtil.set("keytest","val");
        String value =  RedisPoolUtil.get("keytest");

        RedisPoolUtil.setEx("keyex","valex",60*10);

        RedisPoolUtil.expire("keytest",60*20);

        RedisPoolUtil.del("keytest");

        System.out.println("end");

    }

}
