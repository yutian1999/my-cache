/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.init;

import com.cache.persist.Persist2Memory;


/**
 *
 * @author wengyz
 * @version CacheInit.java, v 0.1 2020-10-12 11:34 上午
 */
public class CacheInit {

    public static void init(){
        Persist2Memory.persist2Memory();
    }
}