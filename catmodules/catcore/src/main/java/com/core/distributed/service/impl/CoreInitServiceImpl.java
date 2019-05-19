package com.core.distributed.service.impl;

import com.core.distributed.config.CatConfig;
import com.core.distributed.disruptor.CatTransactionEventPublisher;
import com.core.distributed.service.coordinator.CoordinatorRepository;
import com.core.distributed.service.CoordinatorService;
import com.core.distributed.service.CoreInitService;
import com.core.distributed.service.coordinator.impl.JdbcCoordinatorRepository;
import com.bobo.serializer.CObjectSerializer;
import com.bobo.serializer.impl.CJavaSerializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Iterator;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

public class CoreInitServiceImpl implements CoreInitService {

    private final CoordinatorService coordinatorService;

    private final CatTransactionEventPublisher publisher;

    private final ScheduledService scheduledService;
    /**
     * springBean的上下文容器
     */
    private final ConfigurableApplicationContext context;
    public CoreInitServiceImpl(final CoordinatorService coordinatorService,final CatTransactionEventPublisher publisher,
                               final ScheduledService scheduledService,final ConfigurableApplicationContext context){
        this.coordinatorService = coordinatorService;
        this.publisher = publisher;
        this.scheduledService = scheduledService;
        this.context = context;
    }

    @Override
    public void initialization(CatConfig mythConfig) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> logger.error("cat have error!")));
        try {
            loadSpiSupport(mythConfig);
            publisher.start(mythConfig.getBufferSize());
            coordinatorService.start(mythConfig);
            //如果需要自动恢复 开启线程 调度线程池，进行恢复
            if (mythConfig.getNeedRecover()) {
                scheduledService.scheduledAutoRecover(mythConfig);
            }
        } catch (Exception ex) {
            logger.error("初始化失败:{}",ex.getMessage());
            //非正常关闭
            System.exit(1);
        }
    }

    private void loadSpiSupport(final CatConfig config) {
        final ServiceLoader<CObjectSerializer> objectSerializers = loadAll(CObjectSerializer.class);
        final CObjectSerializer serializer =
                    StreamSupport.stream(objectSerializers.spliterator(),
                        true)
                        .filter(objectSerializer -> Objects.equals(objectSerializer.getScheme(),config.getSerializer()))
                        .findFirst()
                        .orElse(new CJavaSerializer());
        coordinatorService.setSerializer(serializer);
        context.getBeanFactory().registerSingleton(CObjectSerializer.class.getName(),serializer);
        final ServiceLoader<CoordinatorRepository> recoverRepositories = loadAll(CoordinatorRepository.class);
        final CoordinatorRepository repository =
                StreamSupport.stream(recoverRepositories.spliterator(), false)
                        .filter(recoverRepository -> Objects.equals(recoverRepository.getScheme(), config.getRepositorySupport()))
                        .findFirst()
                        .orElse(new JdbcCoordinatorRepository());
        //将CoordinatorRepository实现注入到spring容器
        repository.setSerializer(serializer);
        context.getBeanFactory().registerSingleton(CoordinatorRepository.class.getName(),repository);
    }

    public static <S> S loadFirst(final Class<S> clazz) {
        final ServiceLoader<S> loader = loadAll(clazz);
        final Iterator<S> iterator = loader.iterator();
        if (!iterator.hasNext()) {
            throw new IllegalStateException(String.format(
                    "No implementation defined in /META-INF/services/%s, please check whether the file exists and has the right implementation class!",
                    clazz.getName()));
        }
        return iterator.next();
    }

    public static <S> ServiceLoader<S> loadAll(final Class<S> clazz) {
        return ServiceLoader.load(clazz);
    }
}
