/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.client;

import com.cache.enums.ServerTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author wengyz
 * @version CacheConfiguration.java, v 0.1 2020-10-18 1:28 下午
 */
@Component
public class CacheConfiguration  {

    public static final Map<ServerTypeEnum,CacheClient> clientRoute = new HashMap<>();
    private static Logger logger = LoggerFactory.getLogger(CacheConfiguration.class);

    @Resource
    private Environment environment;

    @PostConstruct
    public void init(){
        String property = environment.getProperty("myCache.type");
        if ("single".equals(property)){
            CacheClient client = CacheClient.getClient("http://" + environment.getProperty("myCache.master.server") + ":8111/cache");
            connect(client);
            clientRoute.put(ServerTypeEnum.single,client);
        }else {
            CacheClient masterClient = CacheClient.getClient("http://" + environment.getProperty("myCache.master.server") + ":8111/cache");
            connect(masterClient);
            clientRoute.put(ServerTypeEnum.master,masterClient);
            CacheClient salveClient = CacheClient.getClient("http://" + environment.getProperty("myCache.salve.server") + ":8111/cache");
            connect(salveClient);
            clientRoute.put(ServerTypeEnum.master,salveClient);
        }
    }

    private void connect(CacheClient salveClient) {
        String result;
        result = salveClient.connect();
        if (!"connect success".equals(result)){
            logger.error("connect MyCache time out");
            throw new RuntimeException("connect MyCache time out");
        }
    }

    public CacheClient fetchClient(ServerTypeEnum type){
        String property = environment.getProperty("myCache.type");
        if ("single".equals(property)){
            return clientRoute.get(ServerTypeEnum.single);
        }else {
            CacheClient client = clientRoute.get(type);
            if (ServerTypeEnum.master.equals(type)){
                String connect = client.connect();
                if ("connect time out".equals(connect)){
                    client = clientRoute.get(ServerTypeEnum.salve);
                }
            }
            return client;
        }
    }
}