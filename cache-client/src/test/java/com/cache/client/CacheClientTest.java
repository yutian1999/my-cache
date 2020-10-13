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

    /**
     * set test
     */
    @Test
    public void setTest()  {
        for (int i = 0; i < 1000; i++) {
            client.set("myCache-" + i, "value-"+i, 20000);
        }
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
        String hello = client.get("myCache-2020");
        System.out.println(hello);
    }

    /**
     * del test
     */
    @Test
    public void delTest() {
        for (int i = 0; i < 1000; i++) {
            client.del("myCache-" + i);
        }
    }
}