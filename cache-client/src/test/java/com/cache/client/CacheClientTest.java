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
        client = CacheClient.getClient("http://192.168.1.45:8111/cache");
    }

    /**
     * set test
     */
    @Test
    public void setTest()  {

        String set = client.set("node", "love", 200000);
        System.out.println(set);
    }

    /**
     * setNx test
     */
    @Test
    public void setNxTest() {
        String hello = client.setNx("lock1", "1", 2000000000);
        System.out.println(hello);
    }

    /**
     * get test
     */
    @Test
    public void getTest() {
        String hello = client.get("node");
        System.out.println(hello);
    }

    /**
     * del test
     */
    @Test
    public void delTest() {
        client.del("node");
    }
}