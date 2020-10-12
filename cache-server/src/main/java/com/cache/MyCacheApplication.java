/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache;

import com.cache.init.CacheInit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * @author wengyz
 * @version MyCacheApplication.java, v 0.1 2020-10-10 10:03 上午
 */
@EnableScheduling
@SpringBootApplication
@Slf4j
public class MyCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyCacheApplication.class,args);
        log.info("myCache init start ...");
        CacheInit.init();
        log.info("myCache init end ...");
    }
}