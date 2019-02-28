package com.app.distributed.service.impl;

import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.app.distributed.CatTransaction;
import com.app.distributed.config.CatConfig;
import com.app.distributed.service.CoordinatorRepository;
import com.app.distributed.service.CoordinatorService;
import com.app.distributed.service.RpcApplicationService;
import com.bobo.base.CatException;

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

    }

    @Override
    public String save(CatTransaction transaction) {
        return null;
    }

    @Override
    public CatTransaction findByTransationId(String id) {
        return null;
    }

    @Override
    public List<CatTransaction> listAllByDelay(Date date) {
        return null;
    }

    @Override
    public boolean remove(String transationId) {
        return false;
    }

    @Override
    public int update(CatTransaction transaction) throws CatException {
        return 0;
    }

    @Override
    public void updateFailTransaction(CatTransaction transaction) throws CatException {

    }

    @Override
    public void updateParticipant(CatTransaction transaction) throws CatException {

    }

    @Override
    public int updateStatus(String transId, Integer status) throws CatException {
        return 0;
    }

    @Override
    public void setSerializer(ObjectSerializer serializer) {

    }
}
