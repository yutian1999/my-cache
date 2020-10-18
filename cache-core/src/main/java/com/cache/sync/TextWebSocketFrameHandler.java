package com.cache.sync;

import com.alibaba.fastjson.JSON;
import com.cache.persist.CachePersist;
import com.cache.resolver.CacheResolver;
import com.cache.sync.transfer.SyncType;
import com.cache.sync.transfer.TransferData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static ChannelHandlerContext channel;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("master receive salve heart data ");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        channel = ctx;
        //是否握手成功，升级为 Websocket 协议
        if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            SocketAddress address = ctx.channel().remoteAddress();
            log.info("salve connect master >> salveAddress = {}",address);
            // 握手成功后要进行文件同步 之后通过命令传播的模式进行主从同步
            sync(ctx);
        }
    }

    private void sync(ChannelHandlerContext ctx) {
        Map<String, String> cache = CacheResolver.cache;
        Map<String, Long> expireMap = CacheResolver.expireMap;
        Set<String> keys = cache.keySet();
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            String content = CachePersist.getContent(key,cache.get(key),expireMap.get(key));
            sb.append(content);
        }
        TransferData data = new TransferData();
        data.setContent(sb.toString());
        data.setType(SyncType.sync);
        ctx.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(data)));
    }

    public static void send(TransferData data){
        if (channel != null){
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(data)));
        }
    }
}
