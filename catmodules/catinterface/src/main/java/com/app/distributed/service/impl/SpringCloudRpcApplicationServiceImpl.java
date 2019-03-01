package com.app.distributed.service.impl;

import com.app.distributed.config.CatConfig;
import com.app.distributed.service.RpcApplicationService;

public class SpringCloudRpcApplicationServiceImpl implements RpcApplicationService {
    @Override
    public String acquireName() {
        return CatConfig.LOCAL;
    }
}
