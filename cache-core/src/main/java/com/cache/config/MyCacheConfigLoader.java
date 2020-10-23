/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.config;

import com.cache.enums.ServerTypeEnum;
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
public class MyCacheConfigLoader {

    public static final Map<String,String> config = new HashMap<>();

    /**
     * 加载配置信息
     */
    public static void loadProperties(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(MyCacheConfigLoader.class.getResource("/myCache.properties").getPath()));
            List<String> lines = reader.lines().collect(Collectors.toList());
            for (String line : lines) {
                String[] conf = line.split("=");
                config.put(conf[0],conf[1]);
            }
            buildConf();
        } catch (Exception e) {
            log.error("loadProperties >> error = ",e);
        }
    }

    public static void buildConf(){
        MyCacheConf.serverType = ServerTypeEnum.valueOf(config.get(MyCacheConfKeys.TYPE));
        MyCacheConf.serverUrl = config.get(MyCacheConfKeys.MASTER_SERVER_URL);
        MyCacheConf.isMaster = config.get(MyCacheConfKeys.IS_MASTER);
        MyCacheConf.IsSalve = config.get(MyCacheConfKeys.IS_SALVE);
    }
}