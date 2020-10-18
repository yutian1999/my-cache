/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.job;

import com.cache.config.MyCacheConf;
import com.cache.enums.ServerTypeEnum;
import com.cache.persist.CachePersist;
import com.cache.resolver.CacheService;
import com.cache.sync.client.SalveClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author wengyz
 * @version ExpireJob.java, v 0.1 2020-10-11 9:53 上午
 */
@Component
@Slf4j
public class CacheJob {

    /**
     * 定期过期key
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void expireDel(){
        CacheService.expire();
    }

    /**
     * 定期持久化
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void persist(){
        CachePersist.persist();
    }

    /**
     * 心跳
     */
    @Scheduled(cron = "0 0/2 * * * ?")
    public void heart(){
        if(MyCacheConf.serverType.equals(ServerTypeEnum.master_salve) && MyCacheConf.IsSalve.equals("true")) {
            try {
                SalveClient.clientInit().send("salve to master health");
            } catch (Exception e) {
                log.error("salve to master error = ",e);
                SalveClient.connect();
            }
        }
    }

}