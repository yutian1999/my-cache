/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.gateway;

import com.cache.config.MyCacheConf;
import com.cache.enums.MyCacheOperateEnum;
import com.cache.enums.ServerTypeEnum;
import com.cache.model.TransferModel;
import com.cache.resolver.CacheService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wengyz
 * @version GatewayController.java, v 0.1 2020-10-10 4:27 下午
 */
@RestController
public class GatewayController {

    /**
     * 服务端入口
     * @param model model
     * @return result
     */
    @RequestMapping("/cache")
    public String gateway(@RequestBody TransferModel model){

        if (model.getOperate().equals(MyCacheOperateEnum.CONNECT)){
            return "connect success";
        }

        if(MyCacheConf.serverType.equals(ServerTypeEnum.master_salve) && MyCacheConf.IsSalve.equals("true")) {
            if (!model.getOperate().equals(MyCacheOperateEnum.GET)){
                return "salve node only have read auth";
            }
        }
        return CacheService.cacheCore(model);
    }
}