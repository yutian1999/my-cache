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
public class CacheClientSalveTest {

    CacheClient salveClient;

    @Before
    public void before(){
        salveClient = CacheClient.getClient("http:/192.168.1.44:8111/cache");
    }

    /**
     * set test
     */
    @Test
    public void setTest()  {

        String hello = salveClient.set("hello", "value-", 20000);
        System.out.println(hello);
    }

    /**
     * setNx test
     */
    @Test
    public void setNxTest() {
        String hello = salveClient.setNx("lock1", "1", 2000000000);
        System.out.println(hello);
    }

    /**
     * get test
     */
    @Test
    public void getTest() {
        String hello = salveClient.get("hello");
        System.out.println(hello);
    }

    /**
     * del test
     */
    @Test
    public void delTest() {
        salveClient.del("lock1");
    }
}