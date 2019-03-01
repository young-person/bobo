
package com.app.distributed.service.coordinator.impl;

import com.app.distributed.CatTransaction;
import com.app.distributed.config.CatConfig;
import com.app.distributed.config.CoordinatorRepositoryAdapter;
import com.app.distributed.config.ZookeeperConfig;
import com.app.distributed.context.RepositoryConvertUtils;
import com.app.distributed.service.coordinator.CoordinatorRepository;
import com.bobo.enums.JTAEnum;
import com.bobo.serializer.CObjectSerializer;
import com.bobo.utils.ComUtils;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

public class ZookeeperCoordinatorRepository implements CoordinatorRepository {

    private static volatile ZooKeeper zooKeeper;

    private static final CountDownLatch LATCH = new CountDownLatch(1);

    private CObjectSerializer objectSerializer;

    private String rootPathPrefix = CatConfig.LOCAL;

    @Override
    public int create(final CatTransaction transaction) {
        try {
            zooKeeper.create(buildRootPath(transaction.getTransId()),
                    RepositoryConvertUtils.convert(transaction, objectSerializer),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            return JTAEnum.SUCCESS.getCode();
        } catch (Exception e) {
            e.printStackTrace();
            return JTAEnum.ERROR.getCode();
        }
    }

    @Override
    public int remove(final String transId) {
        try {
            zooKeeper.delete(buildRootPath(transId), -1);
            return JTAEnum.SUCCESS.getCode();
        } catch (Exception e) {
            e.printStackTrace();
            return JTAEnum.ERROR.getCode();
        }
    }

    @Override
    public int update(final CatTransaction transaction) throws RuntimeException {
        try {
            transaction.setLastTime(new Date());
            transaction.setVersion(transaction.getVersion() + 1);
            zooKeeper.setData(buildRootPath(transaction.getTransId()),
                    RepositoryConvertUtils.convert(transaction, objectSerializer), -1);
            return JTAEnum.SUCCESS.getCode();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateFailTransaction(final CatTransaction transaction) throws RuntimeException {
        update(transaction);
    }

    @Override
    public void updateParticipant(final CatTransaction transaction) throws RuntimeException {
        final String path = ComUtils.buildZookeeperRootPath(rootPathPrefix, transaction.getTransId());
        try {
            byte[] content = zooKeeper.getData(path, false, new Stat());
            if (content != null) {
                final CoordinatorRepositoryAdapter adapter =
                        objectSerializer.deSerialize(content, CoordinatorRepositoryAdapter.class);
                adapter.setContents(objectSerializer.serialize(transaction.getParticipants()));
                zooKeeper.setData(path, objectSerializer.serialize(adapter), -1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateStatus(final String id, final Integer status) throws RuntimeException {
        final String path = ComUtils.buildZookeeperRootPath(rootPathPrefix, id);
        try {
            byte[] content = zooKeeper.getData(path, false, new Stat());
            if (content != null) {
                final CoordinatorRepositoryAdapter adapter =
                        objectSerializer.deSerialize(content, CoordinatorRepositoryAdapter.class);
                adapter.setStatus(status);
                zooKeeper.setData(path, objectSerializer.serialize(adapter), -1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return JTAEnum.SUCCESS.getCode();
    }

    @Override
    public CatTransaction findByTransId(final String transId) {
        try {
            Stat stat = new Stat();
            byte[] content = zooKeeper.getData(buildRootPath(transId), false, stat);
            return RepositoryConvertUtils.transformBean(content, objectSerializer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CatTransaction> listAllByDelay(final Date date) {
        final List<CatTransaction> transactionList = listAll();
        return transactionList.stream()
                .filter(mythTransaction -> mythTransaction.getLastTime().compareTo(date) > 0)
                .filter(mythTransaction -> mythTransaction.getStatus() == JTAEnum.BEGIN.getCode())
                .collect(Collectors.toList());
    }

    private List<CatTransaction> listAll() {
        List<CatTransaction> transactionRecovers = Lists.newArrayList();
        List<String> zNodePaths;
        try {
            zNodePaths = zooKeeper.getChildren(rootPathPrefix, false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (CollectionUtils.isNotEmpty(zNodePaths)) {
            transactionRecovers = zNodePaths.stream()
                    .filter(StringUtils::isNoneBlank)
                    .map(zNodePath -> {
                        try {
                            byte[] content = zooKeeper.getData(buildRootPath(zNodePath), false, new Stat());
                            return RepositoryConvertUtils.transformBean(content, objectSerializer);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }).collect(Collectors.toList());
        }
        return transactionRecovers;
    }

    @Override
    public void init(final String modelName, final CatConfig config) {
        rootPathPrefix = ComUtils.buildZookeeperPathPrefix(modelName);
        connect(config.getZookeeperConfig());
    }

    private void connect(final ZookeeperConfig config) {
        try {
            zooKeeper = new ZooKeeper(config.getHost(), config.getSessionTimeOut(), watchedEvent -> {
                if (watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    // 放开闸门, wait在connect方法上的线程将被唤醒
                    LATCH.countDown();
                }
            });
            LATCH.await();
            Stat stat = zooKeeper.exists(rootPathPrefix, false);
            if (stat == null) {
                zooKeeper.create(rootPathPrefix, rootPathPrefix.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getScheme() {
        return "zookeeper";
    }

    @Override
    public void setSerializer(final CObjectSerializer objectSerializer) {
        this.objectSerializer = objectSerializer;
    }

    private String buildRootPath(final String id) {
        return ComUtils.buildZookeeperRootPath(rootPathPrefix, id);
    }
}
