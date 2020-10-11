/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.model;

/**
 *
 * @author wengyz
 * @version ContentModel.java, v 0.1 2020-10-10 11:01 上午
 */
public class ContentModel {

    private String key;

    private String value;

    private Integer expire;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getExpire() {
        return expire;
    }

    public void setExpire(Integer expire) {
        this.expire = expire;
    }
}