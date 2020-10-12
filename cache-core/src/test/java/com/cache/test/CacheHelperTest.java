/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.test;

import com.cache.help.CacheHelper;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author wengyz
 * @version CacheHelperTest.java, v 0.1 2020-10-12 3:05 下午
 */
public class CacheHelperTest {

    CacheHelper cacheHelper;

    @Before
    public void before(){
        cacheHelper = CacheHelper.init();
    }

    /**
     * set test
     */
    @Test
    public void setTest() {
        String hello = cacheHelper.set("cc", "222", 2000000000);
        System.out.println(hello);
    }

    /**
     * setNx test
     */
    @Test
    public void setNxTest() {
        String hello = cacheHelper.setNx("lock1", "1", 2000000000);
        System.out.println(hello);
    }

    /**
     * get test
     */
    @Test
    public void getTest() {
        String hello = cacheHelper.get("cc");
        System.out.println(hello);
    }

    /**
     * del test
     */
    @Test
    public void delTest() {
        String hello = cacheHelper.del("hello");
        System.out.println(hello);
    }
}