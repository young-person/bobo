package com.app.crawler.riches.producer;

import com.app.crawler.riches.BRichesBase;
import com.app.crawler.riches.pojo.HistoryBean;
import com.app.crawler.riches.pojo.RicheBean;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static com.app.crawler.base.CrawlerDown.SPACE;

public class PersistTxt extends BRichesBase implements Persist {

    @Override
    public void writeHistoryDataToFile(RicheBean bean, List<HistoryBean> datas) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        File file = this.getExcelPath(bean, ".txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        RLoadXml loadXml = new RLoadXml();//如何判断这个日期数据是否有写入
        if (datas.size() == 1) {
            String line = loadXml.readLastLine(file);
            if (StringUtils.isNotBlank(line)) {
                String[] values = line.split(SPACE);
                HistoryBean historyBean = new HistoryBean();
                Field[] fields = historyBean.getClass().getDeclaredFields();
                for (int index = 0; index < fields.length; index++) {
                    Field field = fields[index];
                    BeanUtils.setProperty(historyBean, field.getName(), values[index]);
                }
                if (!historyBean.getDate().equals(datas.get(0).getDate())) {
                    this.writeByLineNum(loadXml, file, datas, 0);
                }
            } else {
                this.writeByLineNum(loadXml, file, datas, 0);
            }
        } else {
            for (int i = 0; i < datas.size(); i++) {
                this.writeByLineNum(loadXml, file, datas, i);
            }
        }
    }

    private void writeByLineNum(RLoadXml loadXml,File file,List<HistoryBean> datas,int i) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        HistoryBean historyBean = datas.get(i);
        List<String> values = loadXml.getBeanValues(historyBean);
        String content = String.join(SPACE, values);
        loadXml.writeFile(file, content);
    }

    @Override
    public List<HistoryBean> readHistoryFromFile(File file) throws IOException, IllegalAccessException, InvocationTargetException {
        FileInputStream inputStream = new FileInputStream(file);
        InputStreamReader streamReader = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        String line = null;
        List<HistoryBean> result = new ArrayList<>();
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (Objects.nonNull(line)) {
                    String[] values = line.split(SPACE);
                    HistoryBean bean = new HistoryBean();
                    Field[] fields = bean.getClass().getDeclaredFields();
                    for (int index = 0; index < fields.length; index++) {
                        Field field = fields[index];
                        BeanUtils.setProperty(bean, field.getName(), values[index]);
                    }
                    result.add(bean);
                }
            }
        } finally {
            IOUtils.closeQuietly(bufferedReader);
            IOUtils.closeQuietly(streamReader);
            IOUtils.closeQuietly(inputStream);
        }
        return result;
    }
}
