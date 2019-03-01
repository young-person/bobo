package com.app.distributed.disruptor;

import com.app.distributed.*;
import com.app.distributed.context.TransactionThreadFactory;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 高并发下 的事务事件消息发布
 */
public class CatTransactionEventPublisher implements DisposableBean {
    private static final Logger logger = LoggerFactory.getLogger(CatTransactionEventPublisher.class);

    private static final int MAX_THREAD = Runtime.getRuntime().availableProcessors() << 1;

    private Executor executor;
    private final CatTransactionEventHandler catTransactionEventHandler;
    private Disruptor<CatTransactionEvent> disruptor;
    public CatTransactionEventPublisher(CatTransactionEventHandler catTransactionEventHandler) {
        this.catTransactionEventHandler = catTransactionEventHandler;
    }
    @Override
    public void destroy() throws Exception {
        disruptor.shutdown();
    }

    public void start(final int bufferSize) {
        disruptor = new Disruptor<>(new CatTransactionEventFactory(), bufferSize, r -> {
            AtomicInteger index = new AtomicInteger(1);
            return new Thread(null, r, "disruptor-thread-" + index.getAndIncrement());
        }, ProducerType.MULTI, new BlockingWaitStrategy());

        executor = new ThreadPoolExecutor(
                MAX_THREAD,
                MAX_THREAD,
                0,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                TransactionThreadFactory.create("cat-log-disruptor",
                        false),
                new ThreadPoolExecutor.AbortPolicy());

        disruptor.handleEventsWith(catTransactionEventHandler);

        disruptor.setDefaultExceptionHandler(new ExceptionHandler<CatTransactionEvent>() {
            @Override
            public void handleEventException(Throwable ex, long sequence, CatTransactionEvent event) {
                logger.error("Disruptor 事件异常:{},{},{}",new Object[]{ event.getType(),event.getTransaction().toString(),
                        ex.getMessage()});
            }

            @Override
            public void handleOnStartException(Throwable ex) {
                logger.error("Disruptor 开始异常");
            }

            @Override
            public void handleOnShutdownException(Throwable ex) {
                logger.error("Disruptor 异常关闭");
            }
        });
        disruptor.start();
    }


    /**
     * publish disruptor event.
     *
     * @param transaction
     * @param type
     */
    public void publishEvent(final CatTransaction transaction, final int type) {
        executor.execute(() -> {
            final RingBuffer<CatTransactionEvent> ringBuffer = disruptor.getRingBuffer();
            ringBuffer.publishEvent(new CatTransactionEventTranslator(type), transaction);
        });

    }

}
