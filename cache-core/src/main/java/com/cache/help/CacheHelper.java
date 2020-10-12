/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.help;

import com.cache.enums.MyCacheOperateEnum;
import com.cache.model.ContentModel;
import com.cache.model.TransferModel;
import com.cache.resolver.CacheService;

/**
 *
 * @author wengyz
 * @version CacheHelper.java, v 0.1 2020-10-12 3:01 下午
 */
public class CacheHelper {

    private static final CacheHelper instance = new CacheHelper();

    private CacheHelper() {
    }

    public static CacheHelper init(){
        return instance;
    }

    /**
     * 添加
     * @param key key
     * @param value value
     * @param expire 过期时间
     * @return 结果
     */
    public String set(String key,String value,int expire) {
        TransferModel model = new TransferModel();
        ContentModel content = new ContentModel();
        content.setKey(key);
        content.setValue(value);
        content.setExpire(expire);
        model.setOperate(MyCacheOperateEnum.SET);
        model.setContent(content);
        return CacheService.cacheCore(model);
    }

    /**
     * 查询
     * @param key key
     * @return value
     */
    public String get(String key) {
        TransferModel model = new TransferModel();
        ContentModel content = new ContentModel();
        content.setKey(key);
        model.setOperate(MyCacheOperateEnum.GET);
        model.setContent(content);
        return CacheService.cacheCore(model);
    }

    /**
     * 删除
     * @param key key
     * @return 返回结果
     */
    public String del(String key){
        TransferModel model = new TransferModel();
        ContentModel content = new ContentModel();
        content.setKey(key);
        model.setOperate(MyCacheOperateEnum.DEL);
        model.setContent(content);
        return CacheService.cacheCore(model);
    }

    /**
     * 分布式锁
     * @param key k
     * @param value v
     * @param expire 过期时间
     * @return 返回值
     */
    public String setNx(String key,String value,int expire) {
        TransferModel model = new TransferModel();
        ContentModel content = new ContentModel();
        content.setKey(key);
        content.setValue(value);
        content.setExpire(expire);
        model.setOperate(MyCacheOperateEnum.SETNX);
        model.setContent(content);
        return CacheService.cacheCore(model);
    }
}