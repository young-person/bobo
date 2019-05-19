package com.core.distributed.service.coordinator.impl;

import com.core.distributed.CatTransaction;
import com.core.distributed.config.CatConfig;
import com.core.distributed.config.CoordinatorRepositoryAdapter;
import com.core.distributed.context.RepositoryConvertUtils;
import com.core.distributed.service.coordinator.CoordinatorRepository;
import com.bobo.enums.JTAEnum;
import com.bobo.serializer.CObjectSerializer;
import com.bobo.utils.CFileUtils;
import com.bobo.utils.ComUtils;
import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class FileCoordinatorRepository implements CoordinatorRepository {

    private String filePath;

    private CObjectSerializer serializer;

    private AtomicBoolean initialized = new AtomicBoolean(false);

    @Override
    public void setSerializer(final CObjectSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public int create(final CatTransaction transaction) {
        writeFile(transaction);
        return JTAEnum.SUCCESS.getCode();
    }

    @Override
    public int remove(final String id) {
        String fullFileName = ComUtils.getFullFileName(filePath, id);
        File file = new File(fullFileName);
        return file.exists() && file.delete() ? JTAEnum.SUCCESS.getCode() : JTAEnum.ERROR.getCode();
    }

    @Override
    public int update(final CatTransaction transaction) throws RuntimeException {
        transaction.setLastTime(new Date());
        transaction.setVersion(transaction.getVersion() + 1);
        transaction.setRetriedCount(transaction.getRetriedCount() + 1);
        writeFile(transaction);
        return JTAEnum.SUCCESS.getCode();
    }

    @Override
    public void updateFailTransaction(final CatTransaction transaction) throws RuntimeException {
        try {
            final String fullFileName = ComUtils.getFullFileName(filePath, transaction.getTransId());
            transaction.setLastTime(new Date());
            CFileUtils.writeFile(fullFileName, RepositoryConvertUtils.convert(transaction, serializer));
        } catch (Exception e) {
            throw new RuntimeException("更新异常");
        }
    }

    @Override
    public void updateParticipant(final CatTransaction transaction) throws RuntimeException {
        try {
            final String fullFileName = ComUtils.getFullFileName(filePath, transaction.getTransId());
            final File file = new File(fullFileName);
            final CoordinatorRepositoryAdapter adapter = readAdapter(file);
            if (Objects.nonNull(adapter)) {
                adapter.setContents(serializer.serialize(transaction.getParticipants()));
            }
            CFileUtils.writeFile(fullFileName, serializer.serialize(adapter));
        } catch (Exception e) {
            throw new RuntimeException("更新异常");
        }

    }

    @Override
    public int updateStatus(final String id, final Integer status) throws RuntimeException {
        try {
            final String fullFileName = ComUtils.getFullFileName(filePath, id);
            final File file = new File(fullFileName);

            final CoordinatorRepositoryAdapter adapter = readAdapter(file);
            if (Objects.nonNull(adapter)) {
                adapter.setStatus(status);
            }
            CFileUtils.writeFile(fullFileName, serializer.serialize(adapter));
            return JTAEnum.SUCCESS.getCode();
        } catch (Exception e) {
            throw new RuntimeException("更新数据异常！");
        }

    }

    @Override
    public CatTransaction findByTransId(final String transId) {
        String fullFileName = ComUtils.getFullFileName(filePath, transId);
        File file = new File(fullFileName);
        try {
            return readTransaction(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<CatTransaction> listAllByDelay(final Date date) {
        final List<CatTransaction> transactionList = listAll();
        return transactionList.stream()
                .filter(tccTransaction -> tccTransaction.getLastTime().compareTo(date) < 0)
                .filter(mythTransaction -> mythTransaction.getStatus() == JTAEnum.BEGIN.getCode())
                .collect(Collectors.toList());
    }

    private List<CatTransaction> listAll() {
        List<CatTransaction> transactionRecoverList = Lists.newArrayList();
        File path = new File(filePath);
        File[] files = path.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                try {
                    CatTransaction transaction = readTransaction(file);
                    transactionRecoverList.add(transaction);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return transactionRecoverList;
    }

    @Override
    public void init(final String modelName, final CatConfig config) {
        filePath = ComUtils.buildFilePath(modelName);
        File file = new File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.mkdirs();
        }
    }

    @Override
    public String getScheme() {
        return CatConfig.create().getRepositorySupport();
    }

    private CatTransaction readTransaction(final File file) throws Exception {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] content = new byte[(int) file.length()];
            fis.read(content);
            return RepositoryConvertUtils.transformBean(content, serializer);
        }
    }

    private CoordinatorRepositoryAdapter readAdapter(final File file) throws Exception {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] content = new byte[(int) file.length()];
            fis.read(content);
            return serializer.deSerialize(content, CoordinatorRepositoryAdapter.class);
        }
    }

    private void writeFile(final CatTransaction transaction) throws RuntimeException {
        for (; ;) {
            if (makeDirIfNecessary()) {
                break;
            }
        }
        try {
            String fileName = ComUtils.getFullFileName(filePath, transaction.getTransId());
            CFileUtils.writeFile(fileName, RepositoryConvertUtils.convert(transaction, serializer));
        } catch (Exception e) {
            throw new RuntimeException("fail to write transaction to local storage", e);
        }
    }

    private boolean makeDirIfNecessary() throws RuntimeException {
        if (!initialized.getAndSet(true)) {
            File rootDir = new File(filePath);
            boolean isExist = rootDir.exists();
            if (!isExist) {
                if (rootDir.mkdir()) {
                    return true;
                } else {
                    throw new RuntimeException(String.format("fail to make root directory, path:%s.", filePath));
                }
            } else {
                if (rootDir.isDirectory()) {
                    return true;
                } else {
                    throw new RuntimeException(String.format("the root path is not a directory, please check again, path:%s.", filePath));
                }
            }
        }
        return true;// 已初始化目录，直接返回true
    }
}
