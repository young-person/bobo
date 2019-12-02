package com.app.crawler.riches.producer;

import com.app.crawler.pojo.Property;
import com.app.crawler.pojo.RName;
import com.app.crawler.riches.pojo.Data;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RLoadXml implements RLoad<Data> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RLoadXml.class);

    public String convertToXml(Object obj,String path) {
        return this.convertToXml(obj, path,"schedule.xml");
    }

    public String convertToXml(Object obj,String path,String name) {
        // 创建输出流
        StringWriter sw = new StringWriter();
        FileOutputStream outputStream = null;
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());

            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
            // 将对象转换成输出流形式的xml
            marshaller.marshal(obj, sw);
            StringBuilder builder = new StringBuilder(path);
            builder.append(name);
            File file = new File(builder.toString());
            outputStream = new FileOutputStream(file);

            outputStream.write(sw.toString().getBytes(),0,sw.toString().getBytes().length);
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(sw);
        }
        return sw.toString();
    }

    public <T> List<String> getBeanValues(T bean) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Field[] fields = bean.getClass().getDeclaredFields();
        List<String> values = new ArrayList<>(fields.length);
        for(int index = 0; index < fields.length; index ++){
            Field field = fields[index];
            field.setAccessible(true);
            String value = null;
            Object o = BeanUtils.getProperty(bean, field.getName());
            if (Objects.nonNull(o)){
                value = o.toString();
            }else{
                value = "";
            }
            values.add(value);
        }
        return values;
    }
    /**
     * 将类型转为 数据模型
     * @param bean
     * @param <T>
     * @return
     */
    public <T> List<Property> converClassToModel(T bean) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Field[] fields = bean.getClass().getDeclaredFields();
        List<Property> datas = new ArrayList<>(fields.length);
        for(int index = 0; index < fields.length; index ++){
            Field field = fields[index];
            field.setAccessible(true);
            RName[] rName = field.getAnnotationsByType(RName.class);
            String mark = null;
            String value = null;
            String name = field.getName();
            if(Objects.nonNull(rName) && rName.length == 1){
                RName rname = rName[0];
                mark = rname.value();
            }
            Object o = BeanUtils.getProperty(bean, name);
            if (Objects.nonNull(o)){
                value = o.toString();
            }else{
                value = "";
            }
            Property p = new Property();
            p.setMark(mark);
            p.setName(name);
            p.setValue(value);
            datas.add(p);
        }
        return datas;
    }

    public <T> T convertModelToClass(T t,List<Property> properties) throws InvocationTargetException, IllegalAccessException {
        Field[] fields = t.getClass().getDeclaredFields();
        for(int index = 0; index < fields.length; index ++){
            Field field = fields[index];
            String name = field.getName();
            field.setAccessible(true);

            for(Property property : properties){
                if (name.equals(property.getName())){
                    property.getName();
                    property.getMark();
                    BeanUtils.setProperty(t, name,property.getValue());
                    break;
                }
            }

        }
        return t;
    }

    @Override
    public Data getDataFromXml(String path) {
        Data data = null;
        try {
            StringBuilder builder = new StringBuilder(path);
            builder.append("schedule.xml");
            JAXBContext context = JAXBContext.newInstance(Data.class);
            Unmarshaller unMar = context.createUnmarshaller();
            File file = new File(builder.toString());
            data = (Data) unMar.unmarshal(file);
        } catch (JAXBException e) {
            LOGGER.error("加载xml错误", e);
        }
        return data;
    }

    public List<String> readFile(String path) throws IOException {
        // 使用一个字符串集合来存储文本中的路径 ，也可用String []数组
        List<String> list = new ArrayList<String>();
        FileInputStream fis = new FileInputStream(path);
        // 防止路径乱码   如果utf-8 乱码  改GBK     eclipse里创建的txt  用UTF-8，在电脑上自己创建的txt  用GBK
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line = "";
        while ((line = br.readLine()) != null) {
            // 如果 t x t文件里的路径 不包含---字符串       这里是对里面的内容进行一个筛选
            if (line.lastIndexOf("---") < 0) {
                list.add(line);
            }
        }
        br.close();
        isr.close();
        fis.close();
        return list;
    }

    public void writeFile(File file, String content) {
        try {
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(file, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content + "\r\n");
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
