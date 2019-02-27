package com.app.distributed.transaction;

import com.app.distributed.CatParticipant;
import com.app.distributed.CatTransaction;
import com.app.distributed.context.TransactionContextBean;
import com.app.distributed.context.TransactionContextLocal;
import com.app.distributed.service.TransactionMessageService;
import com.app.distributed.disruptor.CatTransactionEventPublisher;
import com.bobo.enums.JTAEnum;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

/**
 * 创建事物引擎
 */
public abstract class CatTransactionEngine {
    private static final Logger logger = LoggerFactory.getLogger(CatTransactionEngine.class);
    /**
     * 使用本地线程进行保存
     */
    private static final ThreadLocal<CatTransaction> CURRENT = new ThreadLocal<CatTransaction>();
    /**
     * 注入事物发布事件者
     */
    private CatTransactionEventPublisher publisher;
    /**
     * 注入事物发布服务
     */
    private TransactionMessageService messageService;

    public abstract CatTransactionEventPublisher getPublisher();
    public abstract TransactionMessageService getTransactionMessageService();

    /**
     * 开始项目分布式事物
     * @param point
     */
    public void begin(final ProceedingJoinPoint point) {
        logger.info("开始分布式事物");
        CatTransaction transaction = buildTransaction(point, JTAEnum.START.getCode(),JTAEnum.BEGIN.getCode(), "");
        publisher.publishEvent(transaction, JTAEnum.SAVE.getCode());
        //将当前事务对象信息 存入当前线程上下文
        CURRENT.set(transaction);
        TransactionContextBean contextBean = new TransactionContextBean();
        contextBean.setTransId(transaction.getTransId());
        //设置为发起者角色
        contextBean.setRole(JTAEnum.START.getCode());
        TransactionContextLocal.getInstance().set(contextBean);//将 事物上下文 保存到
    }

    /**
     * 失败事物信息回改
     * @param errorMsg
     */
    public void failTransaction(final String errorMsg) {
        CatTransaction transaction = get();
        if (Objects.nonNull(transaction)) {
            transaction.setStatus(JTAEnum.FAILURE.getCode());
            transaction.setErrorMsg(errorMsg);
            publisher.publishEvent(transaction, JTAEnum.UPDATE_FAIR.getCode());
        }
    }

    /**
     * 参与者
     * @param point
     * @param transactionContext
     */
    public void actorTransaction(final ProceedingJoinPoint point, final TransactionContextBean transactionContext) {
        CatTransaction transaction = buildTransaction(point,JTAEnum.PROVIDER.getCode(),JTAEnum.BEGIN.getCode(),transactionContext.getTransId());
        //发布事务保存事件，异步保存
        publisher.publishEvent(transaction, JTAEnum.SAVE.getCode());
        //事务信息会写到本地线程
        CURRENT.set(transaction);
        transactionContext.setRole(JTAEnum.PROVIDER.getCode());
        TransactionContextLocal.getInstance().set(transactionContext);
    }

    /**
     * 更新事务状态
     * @param status
     */
    public void updateStatus(final int status) {
        CatTransaction transaction = get();
        Optional.ofNullable(transaction)
                .map(t -> {
                    t.setStatus(status);
                    return t;
                }).ifPresent(t -> publisher.publishEvent(t, JTAEnum.UPDATE_STATUS.getCode()));
        transaction.setStatus(JTAEnum.COMMIT.getCode());
    }

    /**
     * 创建一个连接事物对象
     * @param point
     * @param role
     * @param status
     * @param transId
     * @return
     */
    private CatTransaction buildTransaction(final ProceedingJoinPoint point, final int role,final int status, final String transId) {
        CatTransaction mythTransaction;
        if (StringUtils.isNoneBlank(transId)) {
            mythTransaction = new CatTransaction(transId);
        } else {
            mythTransaction = new CatTransaction();
        }
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> clazz = point.getTarget().getClass();
        mythTransaction.setStatus(status);
        mythTransaction.setRole(role);
        mythTransaction.setTargetClass(clazz.getName());
        mythTransaction.setTargetMethod(method.getName());
        return mythTransaction;
    }

    /**
     * 添加事物参与者
     * @param participant
     */
    public void registerParticipant(final CatParticipant participant) {
        init();
        final CatTransaction transaction = get();
        //添加事务参与者
        transaction.registerParticipant(participant);

        publisher.publishEvent(transaction, JTAEnum.UPDATE_PARTICIPANT.getCode());
    }

    /**
     * 具体发送消息
     */
    public void sendMessage() {
        Optional.ofNullable(get()).ifPresent(c -> messageService.sendMessage(c));
    }

    /**
     * 程序初始化
     */
    private void init(){
        publisher = getPublisher();
        messageService = getTransactionMessageService();
    }

    private CatTransaction get(){
        return CURRENT.get();
    }

    /**
     * 判断是否有事物进来
     * @return
     */
    public boolean isBegin() {
        return CURRENT.get() != null;
    }

    /**
     * 清除容器
     */
    public void cleanThreadLocal() {
        CURRENT.remove();
    }
}
