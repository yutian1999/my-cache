/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.resolver;

import com.alibaba.fastjson.JSON;
import com.cache.model.ContentModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author wengyz
 * @version CacheResolver.java, v 0.1 2020-10-10 11:16 上午
 */
public class CacheResolver {

    private static Logger logger = LoggerFactory.getLogger(CacheResolver.class);

    public static final Map<String, String> cache = new HashMap<>();

    public static final Map<String, Long> expireMap = new HashMap<>();

    /**
     * 添加
     *
     * @param content 内容
     * @return 返回值
     */
    public static String put(ContentModel content) {
        cache.put(content.getKey(), content.getValue());
        Integer expire = content.getExpire();
        if (expire != null) {
            long l = System.currentTimeMillis() / 1000;
            expireMap.put(content.getKey(), l + expire);
        }
        return "ok";
    }

    /**
     * 分布式锁
     * @param content 内容
     * @return 返回值
     */
    public synchronized static String setNx(ContentModel content){
        String s = get(content);
        if ("null".equals(s)){
            return put(content);
        }
        return "-1";
    }

    /**
     * 查询
     *
     * @param content 内容
     * @return 返回值
     */
    public static String get(ContentModel content) {
        String key = content.getKey();
        String s = cache.get(key);
        if (s != null) {
            Long ex = expireMap.get(key);
            if (ex == null) {
                return s;
            }
            long curr = System.currentTimeMillis() / 1000;
            if (curr > ex) {
                expireMap.remove(key);
                cache.remove(key);
                return "null";
            }
            return s;
        }
        return "null";
    }

    /**
     * 删除
     *
     * @param content 内容
     * @return 返回值
     */
    public static String del(ContentModel content) {
        cache.remove(content.getKey());
        expireMap.remove(content.getKey());
        return "ok";
    }

    /**
     * 定期清楚过期key
     */
    public static void expire(){
        long start = System.currentTimeMillis();
        if (expireMap.size() == 0){
            return;
        }
        List<String> keys = new ArrayList<>();
        Iterator<Map.Entry<String, Long>> iterator = expireMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Long> next = iterator.next();
            long curr = System.currentTimeMillis() / 1000;
            if (curr > next.getValue()) {
                keys.add(next.getKey());
                iterator.remove();
                cache.remove(next.getKey());
            }
        }
        long end = System.currentTimeMillis();
        logger.info("myCache expire del keys = {},useTime = {}", JSON.toJSONString(keys),(end -start) + "ms");
    }
}