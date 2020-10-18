/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.client;

/**
 *
 * @author wengyz
 * @version MyCacheTemplate.java, v 0.1 2020-10-18 1:03 下午
 */
public interface MyCacheTemplate {

    /**
     * set
     * @param key k
     * @param value v
     * @param expire 过期时间 单位秒
     * @return 返回结果
     */
    String set(String key,String value,Integer expire);

    /**
     * get
     * @param key key
     * @return 返回结果
     */
    String get(String key);

    /**
     * setNx
     * @param key k
     * @param value v
     * @param expire expire
     * @return 返回结果
     */
    String setNx(String key,String value,Integer expire);

    /**
     * del
     * @param key key
     * @return 返回结果
     */
    String del(String key);
}