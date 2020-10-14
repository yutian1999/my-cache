/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.sync.client;

import com.cache.config.MyCacheConfig;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;

import java.net.URI;

/**
 *
 * @author wengyz
 * @version SalveClient.java, v 0.1 2020-10-14 11:31 上午
 */
@Slf4j
public class SalveClient {

    public static WebSocketClient clientInit(){
        try {
            WebSocketClient webSocketClient = new SocketClient(new URI(MyCacheConfig.getConf("myCache.master.server.url"))) ;
            webSocketClient.connect();
            log.info("salve node start success");
            return webSocketClient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}