/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.resolver;
import com.cache.enums.MyCacheOperateEnum;
import com.cache.model.ContentModel;
import com.cache.model.TransferModel;

/**
 *
 * @author wengyz
 * @version CacheService.java, v 0.1 2020-10-10 4:30 下午
 */
public class CacheService {

    /**
     * 缓存核心方法
     * @param model 模型
     * @return 返回
     */
    public static String cacheCore(TransferModel model){
        MyCacheOperateEnum operate = model.getOperate();
        ContentModel content = model.getContent();

        switch (operate){
        case SET:
            return CacheResolver.put(content);
        case GET:
            return CacheResolver.get(content);
        case DEL:
            return CacheResolver.del(content);
        case SETNX:
            return CacheResolver.setNx(content);
        default:
            return null;
    }
    }

    /**
     * 定期过期key
     */
    public static void expire(){
        CacheResolver.expire();
    }
}