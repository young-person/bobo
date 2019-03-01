package com.app.distributed.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.app.distributed.CatTransaction;
import com.app.distributed.config.CatConfig;
import com.app.distributed.context.TransactionThreadFactory;
import com.app.distributed.disruptor.CatTransactionEventPublisher;
import com.app.distributed.service.CoordinatorService;
import com.app.distributed.service.mq.SendMessageService;
import com.bobo.enums.JTAEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledService.class);

    private SendMessageService sendMessageService;

    private CoordinatorService coordinatorService;

    private CatTransactionEventPublisher publisher;

    public ScheduledService(SendMessageService sendMessageService, CoordinatorService coordinatorService, CatTransactionEventPublisher publisher){
        this.sendMessageService = sendMessageService;
        this.coordinatorService = coordinatorService;
        this.publisher = publisher;
    }

    public void scheduledAutoRecover(final CatConfig config) {
        new ScheduledThreadPoolExecutor(1, TransactionThreadFactory.create(CatConfig.CNAME, true))
                .scheduleWithFixedDelay(() -> {
                    logger.info("auto recover execute delayTime:{}", config.getScheduledDelay());
                    try {
                        final List<CatTransaction> mythTransactionList =coordinatorService.listAllByDelay(acquireData(config));
                        if (CollectionUtils.isNotEmpty(mythTransactionList)) {
                            mythTransactionList.forEach(mythTransaction -> {
                                final Boolean success = sendMessageService.sendMessage(mythTransaction);
                                //发送成功 ，更改状态
                                if (success) {
                                    mythTransaction.setStatus(JTAEnum.COMMIT.getCode());
                                    publisher.publishEvent(mythTransaction, JTAEnum.UPDATE_STATUS.getCode());
                                }
                            });
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 30, config.getScheduledDelay(), TimeUnit.SECONDS);

    }

    private Date acquireData(final CatConfig config) {
        return new Date(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - (config.getRecoverDelayTime() * 1000));
    }

}
