package com.app.distributed.context;

import com.app.distributed.CatParticipant;
import com.app.distributed.CatTransaction;
import com.app.distributed.config.CoordinatorRepositoryAdapter;
import com.bobo.serializer.CObjectSerializer;

import java.util.ArrayList;
import java.util.List;

public class RepositoryConvertUtils {
    public static byte[] convert(final CatTransaction transaction, final CObjectSerializer objectSerializer) throws Exception {
        CoordinatorRepositoryAdapter adapter = new CoordinatorRepositoryAdapter();
        adapter.setTransId(transaction.getTransId());
        adapter.setLastTime(transaction.getLastTime());
        adapter.setCreateTime(transaction.getCreateTime());
        adapter.setRetriedCount(transaction.getRetriedCount());
        adapter.setStatus(transaction.getStatus());
        adapter.setTargetClass(transaction.getTargetClass());
        adapter.setTargetMethod(transaction.getTargetMethod());
        adapter.setRole(transaction.getRole());
        adapter.setErrorMsg(transaction.getErrorMsg());
        adapter.setVersion(transaction.getVersion());
        adapter.setContents(objectSerializer.serialize(transaction.getParticipants()));
        return objectSerializer.serialize(adapter);
    }

    @SuppressWarnings("unchecked")
    public static CatTransaction transformBean(final byte[] contents, final CObjectSerializer objectSerializer) throws Exception {
        CatTransaction transaction = new CatTransaction();
        final CoordinatorRepositoryAdapter adapter = objectSerializer.deSerialize(contents, CoordinatorRepositoryAdapter.class);
        List<CatParticipant> participants = objectSerializer.deSerialize(adapter.getContents(), ArrayList.class);
        transaction.setLastTime(adapter.getLastTime());
        transaction.setRetriedCount(adapter.getRetriedCount());
        transaction.setCreateTime(adapter.getCreateTime());
        transaction.setTransId(adapter.getTransId());
        transaction.setStatus(adapter.getStatus());
        transaction.setParticipants(participants);
        transaction.setRole(adapter.getRole());
        transaction.setTargetClass(adapter.getTargetClass());
        transaction.setTargetMethod(adapter.getTargetMethod());
        transaction.setVersion(adapter.getVersion());
        return transaction;
    }
}
