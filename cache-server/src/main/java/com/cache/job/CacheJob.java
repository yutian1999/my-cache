/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.job;

import com.cache.persist.CachePersist;
import com.cache.resolver.CacheService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author wengyz
 * @version ExpireJob.java, v 0.1 2020-10-11 9:53 上午
 */
@Component
public class CacheJob {

    @Scheduled(cron = "0 0/5 * * * ?")
    public void expireDel(){
        CacheService.expire();
    }


    @Scheduled(cron = "0 0/10 * * * ?")
    public void persist(){
        CachePersist.persist();
    }
}