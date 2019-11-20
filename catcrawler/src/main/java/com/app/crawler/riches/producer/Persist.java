package com.app.crawler.riches.producer;

import com.app.crawler.riches.pojo.HistoryBean;
import com.app.crawler.riches.pojo.RicheBean;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface Persist {
    void writeHistoryDataToFile(RicheBean bean, List<HistoryBean> datas) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    List<HistoryBean> readHistoryFromFile(File file) throws IOException, IllegalAccessException, InvocationTargetException;

}