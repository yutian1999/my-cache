/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.config;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author wengyz
 * @version MyCacheConfig.java, v 0.1 2020-10-14 2:02 下午
 */
@Slf4j
public class MyCacheConfig {

    public static final Map<String,String> config = new HashMap<>();

    public static String getConf(String key){
        return config.get(key);
    }

    /**
     * 加载配置信息
     */
    public static void loadProperties(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(MyCacheConfig.class.getResource("/myCache.properties").getPath()));
            List<String> lines = reader.lines().collect(Collectors.toList());
            for (String line : lines) {
                String[] conf = line.split("=");
                config.put(conf[0],conf[1]);
            }
        } catch (Exception e) {
            log.error("loadProperties >> error = ",e);
        }
    }
}