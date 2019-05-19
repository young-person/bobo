package com.core.distributed.service.impl;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.core.distributed.config.CatConfig;
import com.core.distributed.service.RpcApplicationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class DubboRpcApplicationServiceImpl implements RpcApplicationService {

    private final ApplicationConfig applicationConfig;

    @Autowired(required = false)
    public DubboRpcApplicationServiceImpl(final ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Override
    public String acquireName() {
        return Optional.ofNullable(applicationConfig).orElse(new ApplicationConfig(CatConfig.LOCAL)).getName();
    }
}
