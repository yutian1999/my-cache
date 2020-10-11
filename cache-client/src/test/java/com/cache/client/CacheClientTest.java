/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.client;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author wengyz
 * @version CacheClientTest.java, v 0.1 2020-10-10 5:38 下午
 */
public class CacheClientTest {

    CacheClient client;

    @Before
    public void before(){
        client = CacheClient.getClient("http:/127.0.0.1:8111/cache");
    }

    @Test
    public void putTest() throws Exception {
        String hello = client.put("ccc", "222", 20);
        System.out.println(hello);
    }

    @Test
    public void getTest() throws Exception {
        String hello = client.get("hello");
        System.out.println(hello);
    }

    @Test
    public void delTest() throws Exception {
        String hello = client.del("hello");
        System.out.println(hello);
    }
}