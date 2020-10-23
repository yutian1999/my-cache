/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.sync.client;
import com.cache.config.MyCacheConf;
import lombok.extern.slf4j.Slf4j;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author wengyz
 * @version SalveClient.java, v 0.1 2020-10-14 11:31 上午
 */
@Slf4j
public class SalveClient {

    private  static SocketClient webSocketClient = null;

    public static synchronized SocketClient clientInit() throws URISyntaxException {

        if (webSocketClient == null){
            webSocketClient = new SocketClient(new URI(buildUrl())) ;
        }
        return webSocketClient;
    }

    public static void  connect(){
        try {
            webSocketClient = new SocketClient(new URI(buildUrl())) ;
            webSocketClient.connect();
            Thread.sleep(1000);
        } catch (Exception ex) {
            log.error("salve to master error = ",ex);
        }
    }

    public static String buildUrl(){
        return "ws://" + MyCacheConf.serverUrl + ":9999/sync";
    }
}