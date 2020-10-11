/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.resolver;

import com.alibaba.fastjson.JSON;
import com.cache.model.ContentModel;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author wengyz
 * @version CacheResolver.java, v 0.1 2020-10-10 11:16 上午
 */
@Slf4j
public class CacheResolver {

    public static final Map<String, String> cache = new HashMap<>();

    public static final Map<String, Integer> expireMap = new HashMap<>();

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
            int l = (int) (System.currentTimeMillis() / 1000);
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
            Integer ex = expireMap.get(key);
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
        return "ok";
    }

    /**
     * 定期清楚过期key
     */
    public static void expire(){
        if (expireMap == null || expireMap.size() == 0){
            return;
        }
        List<String> keys = new ArrayList<>();
        Iterator<Map.Entry<String, Integer>> iterator = expireMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Integer> next = iterator.next();
            long curr = System.currentTimeMillis() / 1000;
            if (curr > next.getValue()) {
                keys.add(next.getKey());
                iterator.remove();
                cache.remove(next.getKey());
            }
        }
        log.info("schedule del key = {}", JSON.toJSONString(keys));
    }
}