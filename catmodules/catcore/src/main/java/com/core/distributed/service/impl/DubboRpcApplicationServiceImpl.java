package com.app.distributed.service.impl;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.app.distributed.config.CatConfig;
import com.app.distributed.service.RpcApplicationService;
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
