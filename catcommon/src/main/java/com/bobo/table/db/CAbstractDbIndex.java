package com.bobo.table.db;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 针对程序抽象优化策略
 */
public abstract class CAbstractDbIndex<T> implements CDbIndex {

    private static CopyOnWriteArrayList arrayList = new CopyOnWriteArrayList();
    private static AtomicIntegerArray array = new AtomicIntegerArray(arrayList.size());


}
