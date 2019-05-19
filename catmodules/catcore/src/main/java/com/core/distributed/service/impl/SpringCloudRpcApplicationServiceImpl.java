package com.core.distributed.service.impl;

import com.core.distributed.config.CatConfig;
import com.core.distributed.service.RpcApplicationService;

public class SpringCloudRpcApplicationServiceImpl implements RpcApplicationService {
    @Override
    public String acquireName() {
        return CatConfig.LOCAL;
    }
}
