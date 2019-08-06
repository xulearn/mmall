package com.mmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TokenCache {

    public static final String TOKEN_PREFIX = "token_";

    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);

    //本地缓存
    //LRU算法
    private static LoadingCache<String,String> localCache = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000).expireAfterAccess(24,TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                //1、默认的数据加载实现，当调用get取值的时候，如果key没有对应的值，就调用这个方法实现
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });

    //相当于把key,value都放到LoadingCache<String,String> localCache 这个容器中
    public static void setKey(String key,String value){
        localCache.put(key,value);
    }

    public static String getKey(String key){
        String value= null;
        try {
            value = localCache.get(key);
            if("null".equals(value)){   //对应上面注释1
                return null;
            }
            return value;

        }catch (Exception e){
            logger.error("localCache get error", e);
        }
        return null;
    }

}
