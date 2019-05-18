package com.app.distributed.service;

import com.app.distributed.CatTransaction;
import com.app.distributed.config.CatConfig;
import com.bobo.base.CatException;
import com.bobo.serializer.CObjectSerializer;

import java.util.Date;
import java.util.List;

/**
 * 调度服务操作
 */
public interface CoordinatorService {
    /**
     * 配置
     * @param config
     * @throws CatException
     */
    public void start(CatConfig config) throws CatException;

    /**
     * 保存事物
     * @param transaction
     * @return
     */
    public String save(CatTransaction transaction);

    /**
     * 查找事物
     * @param id
     * @return
     */
    public CatTransaction findByTransationId(String id);

    /**
     * 查找时间段的事务
     * @param date
     * @return
     */
    List<CatTransaction> listAllByDelay(Date date);

    /**
     * 移除事务
     * @param transationId
     * @return
     */
    boolean remove(String transationId);

    /**
     * 更新事务
     * @param transaction
     * @return
     * @throws CatException
     */
    int update(CatTransaction transaction) throws CatException;

    /**
     * 更新失败事务
     * @param transaction
     * @throws CatException
     */
    void updateFailTransaction(CatTransaction transaction) throws CatException;

    /**
     * 更新策略事务
     * @param transaction
     * @throws CatException
     */
    void updateParticipant(CatTransaction transaction) throws CatException;

    /**
     * 更新事务状态
     * @param transId
     * @param status
     * @return
     * @throws CatException
     */
    int updateStatus(String transId, Integer status) throws CatException;

    /**
     * 进行序列化
     * @param serializer
     */
    void setSerializer(CObjectSerializer serializer);
}
