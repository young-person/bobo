package com.bobo.table.handler.impl;

import com.bobo.base.BaseClass;
import com.bobo.base.CatException;
import com.bobo.constant.Measure;
import com.bobo.table.bean.Baseid;
import com.bobo.table.bean.Condition;
import com.bobo.table.bean.Dimension;
import com.bobo.table.db.CDbIndex;
import com.bobo.table.db.DBean;
import com.bobo.table.handler.Actuator;
import com.bobo.table.handler.BasicFactory;
import com.bobo.table.handler.ParseBase;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public abstract class ParseBaseFactory extends BaseClass implements ParseBase<Condition> {

    interface BaseidService {
        /**
         * 处理当前值
         *
         * @param baseid
         */
        void handle(Baseid baseid);
    }

    protected Actuator actuator;
    protected CDbIndex dbIndex;

    protected boolean sure = false;

    public abstract BaseidService getBaseidService();

    protected void parseDimension(List<Dimension> dimensions) {
        if (Objects.nonNull(dimensions) && dimensions.isEmpty()) {
            for (Dimension dimension : dimensions) {
                this.getDimension(dimension);
            }
        }
    }

    /**
     * 获取当前维度值
     *
     * @param dimension
     */
    private void getDimension(Dimension dimension) {
        if (Objects.nonNull(dimension)) {
            StringBuilder builder = new StringBuilder();
            builder.append("select ");
            builder.append(dimension.getId());
            List<DBean> result = BasicFactory.getResult(actuator, builder.toString());
            dimension.setResult(result);
            getDimension(dimension);
        }
    }

    /**
     * 解析表达式 为baseid
     * 如果baseids 为空就直接解析 它为 数据库存储 方式
     * 如果 Corns不为空且baseids不为空 sql为空还是数据库方式 sql 不为空则为直接sql方式
     *
     * @param condition
     */
    public Condition parseCorn(Condition condition) {
        if (null == condition.getCorns())
            throw new CatException("表达式长度不能为空");

        final List<Baseid> allBaseid = new ArrayList<>();
        final LinkedHashMap<String, List<Baseid>> relativeMap = new LinkedHashMap<>();

        if (!condition.getBaseids().isEmpty()) {

            for (Baseid baseid : condition.getBaseids()) {
                if (StringUtils.isBlank(baseid.getId())) {
                    throw new CatException("baseid唯一值不能为空");
                }
            }
            boolean sign = true;
            for (Baseid baseid : condition.getBaseids()) {
                if (StringUtils.isNotBlank(baseid.getSql())) {
                    sign = false;
                }
            }
            if (sign) {
                int k = 0;
                List<String> notIn = new ArrayList<>();
                for (Baseid baseid : condition.getBaseids()) {
                    Baseid value = DataCacheUtil.getInstance().getBaseidMap().get(baseid.getId());

                    if (Objects.isNull(value)) {
                        k = k + 1;
                        notIn.add(baseid.getId());
                    } else {
                        this.parseCorn(condition, relativeMap, (baseids, id, start, end) -> {
                            if (id.equals(value.getId())) {
                                value.setStart(start);
                                value.setEnd(end);
                                if (Objects.nonNull(getBaseidService()))
                                    getBaseidService().handle(value);
                                baseids.add(baseid);
                                if (!allBaseid.contains(value))
                                    allBaseid.add(value);
                            }
                        });
                    }
                }
                if (k > 0) {
                    LOGGER.error("ids is not exist in data bank ... ids:[{}] ", notIn);
                    throw new CatException("数据点不存在且数据点对应查询语句为空");
                }
            } else {
                // sql 方式
                this.parseCorn(condition, relativeMap, (baseids, id, start, end) -> {
                    Baseid baseid = new Baseid();
                    baseid.setId(id);
                    baseid.setStart(start);
                    baseid.setEnd(end);

                    if (Objects.nonNull(getBaseidService()))
                        getBaseidService().handle(baseid);
                    baseids.add(baseid);
                    if (!allBaseid.contains(baseid))
                        allBaseid.add(baseid);
                });

                for (Baseid baseid : condition.getBaseids()) {
                    boolean flag = true;
                    for (Baseid b : allBaseid) {
                        if (baseid.getId().equals(b.getId())){
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        throw new CatException("当数据表格配置项为sql，需要保证定义表达式项里面数据点有对应baseid的sql项。不然无法生成表格列");
                    }
                }
                sure = true;
            }

        } else {
            this.parseCorn(condition, relativeMap, (baseids, id, start, end) -> {
                Baseid baseid = DataCacheUtil.getInstance().getBaseidMap().get(id);
                baseid.setStart(start);
                baseid.setEnd(end);

                if (Objects.nonNull(getBaseidService()))
                    getBaseidService().handle(baseid);
                baseids.add(baseid);
                if (!allBaseid.contains(baseid))
                    allBaseid.add(baseid);
            });
        }

        condition.setRelativeMap(relativeMap);
        condition.setBaseids(allBaseid);
        return condition;
    }


    private void parseCorn(Condition condition, LinkedHashMap<String, List<Baseid>> relativeMap, Service service) {

        for (String s : condition.getCorns()) {
            int start = 0;
            int end = 0;
            List<Baseid> baseids = new ArrayList<>();
            for (int i = 0; i < s.length(); i++) {
                char str = s.charAt(i);
                boolean flag = true;
                for (int index = 0; index < Measure.Accord.length; index++) {
                    if (Measure.Accord[index].equals(String.valueOf(str))) {
                        flag = false;
                        end = i;
                        break;
                    }
                }
                if (!flag) {
                    if (start != end) {
                        String id = s.substring(start, end);
                        service.handle(baseids, id, start, end);
                    }
                    start = end;
                }
            }
            relativeMap.put(s, baseids);
        }
    }

    private interface Service {
        void handle(List<Baseid> baseids, String id, int start, int end);
    }
}
