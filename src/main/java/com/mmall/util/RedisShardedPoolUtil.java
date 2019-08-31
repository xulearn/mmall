package com.mmall.util;

import com.mmall.common.RedisShardedPool;
import com.mmall.common.RedisShardedPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

@Slf4j
public class RedisShardedPoolUtil {

    public static Long expire(String key,int exTime){ //设置key的有效期，单位秒
        ShardedJedis jedis = null;
        Long result = null;

        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.expire(key,exTime);//核心代码
        } catch (Exception e) {
            log.error("expire key:{} error",key,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;  //null
        }
        RedisShardedPool.returnResource(jedis);
        return result;  //message
    }

    public static String setEx(String key,String value,int exTime){ //exTime单位是秒
        ShardedJedis jedis = null;
        String result = null;

        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.setex(key,exTime,value);//核心代码
        } catch (Exception e) {
            log.error("setex key:{} value:{} error",key,value,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;  //null
        }
        RedisShardedPool.returnResource(jedis);
        return result;  //message
    }

    public static String set(String key,String value){
        ShardedJedis jedis = null;
        String result = null;

        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.set(key,value);//核心代码
        } catch (Exception e) {
            log.error("set key:{} value:{} error",key,value,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;  //null
        }
        RedisShardedPool.returnResource(jedis);
        return result;  //message
    }

    public static String get(String key){
        ShardedJedis jedis = null;
        String result = null;

        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.get(key);//核心代码
        } catch (Exception e) {
            log.error("get key:{}  error",key,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;  //null
        }
        RedisShardedPool.returnResource(jedis);
        return result;  //message
    }

    public static Long del(String key){
        ShardedJedis jedis = null;
        Long result = null;

        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.del(key);//核心代码
        } catch (Exception e) {
            log.error("get key:{}  error",key,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;  //null
        }
        RedisShardedPool.returnResource(jedis);
        return result;  //message
    }

    public static void main(String[] args) {
        ShardedJedis jedis = RedisShardedPool.getJedis();
        RedisShardedPoolUtil.set("keytest","val");
        String value =  RedisShardedPoolUtil.get("keytest");

        RedisShardedPoolUtil.setEx("keyex","valex",60*10);

        RedisShardedPoolUtil.expire("keytest",60*20);

        RedisShardedPoolUtil.del("keytest");

        System.out.println("end");

    }

}
