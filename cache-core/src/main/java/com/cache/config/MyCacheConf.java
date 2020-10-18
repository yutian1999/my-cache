/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.config;

import com.cache.enums.ServerTypeEnum;
import lombok.Data;

/**
 *
 * @author wengyz
 * @version MyCacheConf.java, v 0.1 2020-10-16 4:36 下午
 */
@Data
public class MyCacheConf {

    public static String serverUrl;

    public static String isMaster;

    public static String IsSalve;

    public static ServerTypeEnum serverType;


}