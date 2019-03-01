package com.app.distributed.service;

import com.app.distributed.config.CatConfig;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class TransactionBootstrap extends CatConfig implements ApplicationContextAware {

    private CoreInitService coreInitService;

    public TransactionBootstrap(final CoreInitService coreInitService) {
        this.coreInitService = coreInitService;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        coreInitService.initialization(this);
    }
}
