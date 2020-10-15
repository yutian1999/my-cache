/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.sync.client;

import com.alibaba.fastjson.JSON;
import com.cache.help.CacheHelper;
import com.cache.persist.Persist2Memory;
import com.cache.sync.transfer.TransferData;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 *
 * @author wengyz
 * @version SocketClient.java, v 0.1 2020-10-14 1:45 下午
 */
@Slf4j
public class SocketClient extends WebSocketClient {

    public SocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handShakeData) {
        log.info("connect master success");
    }

    @Override
    public void onMessage(String message) {
        log.info("receive message = {}",message);
        TransferData data = JSON.parseObject(message, TransferData.class);
        String[] content = data.getContent().split(":");
        switch (data.getType()){
            case add:
                CacheHelper.init().set(content[0],content[1],Integer.parseInt(content[2].replace(",","")));
                return;
            case del:
                CacheHelper.init().del(content[0]);
                return;
            case sync:
                Persist2Memory.persist2Memory(data.getContent());
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        log.info("connect out");
    }

    @Override
    public void onError(Exception ex) {
        log.info("connect error = {}",ex.getMessage());
        // todo 断线后重连
    }
}