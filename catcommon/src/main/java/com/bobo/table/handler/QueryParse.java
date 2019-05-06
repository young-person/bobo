package com.bobo.table.handler;

import com.bobo.table.bean.Baseid;
import com.bobo.table.bean.SimpleResult;

import java.util.List;

public interface QueryParse<E> {

    public SimpleResult parse(E result, String where, String dimensions, List<Baseid> baseids, String from);
}
