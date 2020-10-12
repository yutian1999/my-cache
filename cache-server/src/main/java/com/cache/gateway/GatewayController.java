/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.gateway;

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
        return CacheService.cacheCore(model);
    }
}