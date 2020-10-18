/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.client;

import com.cache.enums.ServerTypeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *
 * @author wengyz
 * @version MyCacheTemplateImpl.java, v 0.1 2020-10-18 1:25 下午
 */
@Component
public class MyCacheTemplateImpl implements MyCacheTemplate {

    @Resource
    private CacheConfiguration cacheConfiguration;


    @Override
    public String set(String key, String value, Integer expire) {
        CacheClient client = cacheConfiguration.fetchClient(ServerTypeEnum.master);
        return client.set(key,value,expire);
    }

    @Override
    public String get(String key) {
        CacheClient client = cacheConfiguration.fetchClient(ServerTypeEnum.salve);
        return client.get(key);
    }

    @Override
    public String setNx(String key, String value, Integer expire) {
        CacheClient client = cacheConfiguration.fetchClient(ServerTypeEnum.master);
        return client.setNx(key,value,expire);
    }

    @Override
    public String del(String key) {
        CacheClient client = cacheConfiguration.fetchClient(ServerTypeEnum.master);
        return client.del(key);
    }
}