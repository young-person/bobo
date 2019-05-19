package com.core.distributed.service;

import com.core.distributed.config.CatConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface CoreInitService {
    public static  final Logger logger = LoggerFactory.getLogger(CoreInitService.class);
    void initialization(CatConfig mythConfig);
}
