package com.app.distributed.service;

import com.app.distributed.CatTransaction;
import com.app.distributed.config.CatConfig;
import com.bobo.serializer.CObjectSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * 协调者
 */
public interface CoordinatorRepository {

    public static final Logger logger = LoggerFactory.getLogger(CoordinatorRepository.class);
    /**
     * 创建事物
     * @param transaction
     * @return
     */
    int create(CatTransaction transaction);

    /**
     * 移除一个事物
     * @param transId
     * @return
     */
    int remove(String transId);


    /**
     * 更新一个事务
     * @param transaction
     * @return
     */
    int update(CatTransaction transaction) throws RuntimeException;


    /**
     * 更新失败 事务
     * @param transaction
     * @throws RuntimeException
     */
    void updateFailTransaction(CatTransaction transaction) throws RuntimeException;


    /***
     * 更新参与者事物
     * @param transaction
     * @throws RuntimeException
     */
    void updateParticipant(CatTransaction transaction) throws RuntimeException;


    /**
     * 更新状态
     * @param transId
     * @param status
     * @return
     * @throws RuntimeException
     */
    int updateStatus(String transId, Integer status) throws RuntimeException;

    /**
     * 查找事务
     * @param transId
     * @return
     */
    CatTransaction findByTransId(String transId);

    /**
     * 事务延迟
     * @param date
     * @return
     */
    List<CatTransaction> listAllByDelay(Date date);


    void init(String modelName, CatConfig config) throws RuntimeException;

    /**
     * 获取约束
     * @return
     */
    String getScheme();

    /**
     * 设置序列化
     * @param serializer
     */
    void setSerializer(CObjectSerializer serializer);
}
