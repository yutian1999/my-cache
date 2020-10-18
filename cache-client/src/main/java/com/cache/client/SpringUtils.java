/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.client;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;

/**
 *
 * @author wengyz
 * @version SpringUtils.java, v 0.1 2020-10-18 1:17 下午
 */
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    @Nullable
    public static <T> T fetchBean(Class<T> clazz) {
        try {
            return context.getBean(clazz);
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public static <T> T fetchBean(String beanName, Class<T> clazz) {
        try {
            return context.getBean(beanName, clazz);
        } catch (Exception e) {
            return null;
        }
    }

}