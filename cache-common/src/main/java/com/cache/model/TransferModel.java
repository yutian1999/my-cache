/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.model;

import com.cache.enums.MyCacheOperateEnum;

/**
 *
 * @author wengyz
 * @version TransferModel.java, v 0.1 2020-10-10 10:51 上午
 */
public class TransferModel {

    /**
     * 操作
     */
    private MyCacheOperateEnum operate;
    /**
     * 内容
     */
    private ContentModel content;

    public MyCacheOperateEnum getOperate() {
        return operate;
    }

    public ContentModel getContent() {
        return content;
    }

    public void setOperate(MyCacheOperateEnum operate) {
        this.operate = operate;
    }

    public void setContent(ContentModel content) {
        this.content = content;
    }
}