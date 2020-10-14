/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.sync.transfer;

/**
 *
 * @author wengyz
 * @version TransferData.java, v 0.1 2020-10-14 8:32 下午
 */
public class TransferData {

    /**
     * 传输内容
     */
    private String content;

    /**
     * 传输类型
     */
    private SyncType type;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SyncType getType() {
        return type;
    }

    public void setType(SyncType type) {
        this.type = type;
    }
}