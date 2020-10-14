/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.init;

import com.cache.config.MyCacheConfig;
import com.cache.persist.Persist2Memory;
import com.cache.sync.WebSocketServer;
import com.cache.sync.client.SalveClient;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;


/**
 *
 * @author wengyz
 * @version CacheInit.java, v 0.1 2020-10-12 11:34 上午
 */
@Slf4j
public class CacheInit {

    public static void init(){
        // 加载配置信息
        MyCacheConfig.loadProperties();
        log.info("load properties success");
        // 持久化数据加载到内存
        Persist2Memory.persist2Memory();
        log.info("load persist data success");

        // 主从同步socket服务端启动
        if(MyCacheConfig.getConf("myCache.master").equals("true")){
            CompletableFuture.runAsync(()-> WebSocketServer.inst().start());
        }

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 主从同步socket客户端启动
        if(MyCacheConfig.getConf("myCache.salve").equals("true")){
            CompletableFuture.runAsync(()->SalveClient.clientInit());
        }
    }
}