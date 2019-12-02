package com.app.crawler.riches.producer;

import com.app.crawler.riches.BRichesBase;
import com.app.crawler.riches.pojo.HistoryBean;
import com.app.crawler.riches.pojo.RicheBean;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.app.crawler.base.CrawlerDown.SPACE;

public class PersistTxt extends BRichesBase implements Persist {

    @Override
    public void writeHistoryDataToFile(RicheBean bean, List<HistoryBean> datas) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        File file = this.getExcelPath(bean,".txt");
        if (file.exists()){
            file.delete();
        }
        file.createNewFile();
        RLoadXml loadXml = new RLoadXml();
        for (int i = 0; i < datas.size(); i++) {
            HistoryBean historyBean = datas.get(i);
            List<String> values = loadXml.getBeanValues(historyBean);
            String content = String.join(SPACE, values);
            loadXml.writeFile(file,content);
        }
    }

    @Override
    public List<HistoryBean> readHistoryFromFile(File file) throws IOException, IllegalAccessException, InvocationTargetException {
        FileInputStream inputStream = new FileInputStream(file);
        InputStreamReader streamReader = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        String line = null;
        List<HistoryBean> result = new ArrayList<>();
        try{
            while ((line = bufferedReader.readLine()) != null) {
                if (Objects.nonNull(line)){
                    String[] values = line.split(SPACE);
                    HistoryBean bean = new HistoryBean();
                    Field[] fields = bean.getClass().getDeclaredFields();
                    for(int index = 0; index < fields.length; index ++){
                        Field field = fields[index];
                        BeanUtils.setProperty(bean,field.getName(),values[index]);
                    }
                    result.add(bean);
                }
            }
        }finally {
            IOUtils.closeQuietly(bufferedReader);
            IOUtils.closeQuietly(streamReader);
            IOUtils.closeQuietly(inputStream);
        }
        return result;
    }
}
