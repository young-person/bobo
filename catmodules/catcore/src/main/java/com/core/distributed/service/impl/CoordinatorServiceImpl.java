package com.core.distributed.service.impl;

import com.core.distributed.CatTransaction;
import com.core.distributed.config.CatConfig;
import com.core.distributed.service.CoordinatorService;
import com.core.distributed.service.RpcApplicationService;
import com.core.distributed.service.coordinator.CoordinatorRepository;
import com.bobo.base.CatException;
import com.bobo.serializer.CObjectSerializer;
import com.bobo.utils.SpringContextUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
/**
 * 协调者 服务实现
 */
public class CoordinatorServiceImpl implements CoordinatorService {

    private CoordinatorRepository coordinatorRepository;

    private final RpcApplicationService rpcApplicationService;

    public CoordinatorServiceImpl(final RpcApplicationService rpcApplicationService){
        this.rpcApplicationService = rpcApplicationService;
    }

    @Override
    public void start(CatConfig config) throws CatException {
        coordinatorRepository = SpringContextUtil.getBean(CoordinatorRepository.class);
        final String repositorySuffix = buildRepositorySuffix(config.getRepositorySuffix());
        //初始化spi 协调资源存储
        coordinatorRepository.init(repositorySuffix, config);
    }

    @Override
    public String save(CatTransaction transaction) {
        final int rows = coordinatorRepository.create(transaction);
        if (rows > 0) {
            return transaction.getTransId();
        }
        return null;
    }

    @Override
    public CatTransaction findByTransationId(String id) {
        return coordinatorRepository.findByTransId(id);
    }

    @Override
    public List<CatTransaction> listAllByDelay(Date date) {
        return coordinatorRepository.listAllByDelay(date);
    }

    @Override
    public boolean remove(String transationId) {
        return coordinatorRepository.remove(transationId) > 0;
    }

    @Override
    public int update(CatTransaction transaction) throws CatException {
        return coordinatorRepository.update(transaction);
    }

    @Override
    public void updateFailTransaction(CatTransaction transaction) throws CatException {
        coordinatorRepository.updateFailTransaction(transaction);
    }

    @Override
    public void updateParticipant(CatTransaction transaction) throws CatException {
        coordinatorRepository.updateParticipant(transaction);
    }

    @Override
    public int updateStatus(String transId, Integer status) throws CatException {
        return coordinatorRepository.updateStatus(transId, status);
    }

    @Override
    public void setSerializer(CObjectSerializer serializer) {

    }

    private String buildRepositorySuffix(final String repositorySuffix) {
        if (StringUtils.isNoneBlank(repositorySuffix)) {
            return repositorySuffix;
        } else {
            return rpcApplicationService.acquireName();
        }
    }
}
