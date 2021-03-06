/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.client;
import com.alibaba.fastjson.JSON;
import com.cache.enums.MyCacheOperateEnum;
import com.cache.model.ContentModel;
import com.cache.model.TransferModel;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author wengyz
 * @version CacheClient.java, v 0.1 2020-10-10 1:39 下午
 */
public class CacheClient {

    private static Logger logger = LoggerFactory.getLogger(CacheClient.class);

    private static String requestUrl;

    public static CacheClient getClient(String url){
        return new CacheClient(url);
    }

    private CacheClient(String url) {
        requestUrl = url;
    }

    /**
     * 底层请求
     * @param param 参数
     * @throws Exception 异常
     */
    private String post(String param) {
        try{
            OkHttpClient client = new OkHttpClient();
            MediaType json = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(json, param);
            Request req = new Request.Builder()
                    .url(requestUrl)
                    .post(body)
                    .build();
            Response response = client.newCall(req).execute();
            return response.body().string();
        }catch (Exception e){
            logger.error("request myCache error = ",e);
        }
        return "connect time out";
    }

    /**
     * 添加
     * @param key key
     * @param value value
     * @param expire 过期时间
     * @return 结果
     */
    public String set(String key,String value,int expire) {
        TransferModel model = new TransferModel();
        ContentModel content = new ContentModel();
        content.setKey(key);
        content.setValue(value);
        content.setExpire(expire);
        model.setOperate(MyCacheOperateEnum.SET);
        model.setContent(content);
        return post(JSON.toJSONString(model));
    }

    /**
     * 查询
     * @param key key
     * @return value
     */
    public String get(String key) {
        TransferModel model = new TransferModel();
        ContentModel content = new ContentModel();
        content.setKey(key);
        model.setOperate(MyCacheOperateEnum.GET);
        model.setContent(content);
        return post(JSON.toJSONString(model));
    }

    /**
     * 删除
     * @param key key
     * @return 返回结果
     */
    public String del(String key) {
        TransferModel model = new TransferModel();
        ContentModel content = new ContentModel();
        content.setKey(key);
        model.setOperate(MyCacheOperateEnum.DEL);
        model.setContent(content);
        return post(JSON.toJSONString(model));
    }

    /**
     * 分布式锁
     * @param key k
     * @param value v
     * @param expire 过期时间
     * @return 返回值
     */
    public String setNx(String key,String value,int expire) {
        TransferModel model = new TransferModel();
        ContentModel content = new ContentModel();
        content.setKey(key);
        content.setValue(value);
        content.setExpire(expire);
        model.setOperate(MyCacheOperateEnum.SETNX);
        model.setContent(content);
        return post(JSON.toJSONString(model));
    }

    /**
     * 连接服务端
     * @return 返回结果
     */
    public String connect() {
        TransferModel model = new TransferModel();
        model.setOperate(MyCacheOperateEnum.CONNECT);
        return post(JSON.toJSONString(model));
    }
}