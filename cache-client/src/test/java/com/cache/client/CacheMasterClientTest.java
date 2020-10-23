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
public class CacheMasterClientTest {

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
//        for (int i = 0; i < 10; i++) {
//            String set = client.set("key-"+i, "value-"+i, 200000);
//        }
        String set = client.set("master2salve", "success", 200000);
//        System.out.println(set);
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
        for (int i = 0; i < 1000; i++) {
            String set = client.del("key-"+i);
        }
    }
}