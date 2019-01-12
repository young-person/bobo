package com.app.filemonitor;

import com.app.filemonitor.run.WatchTask;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.WatchEvent;
import java.util.List;

public interface WatcherPathCallback {
    public static Logger logger = LoggerFactory.getLogger(WatcherPathCallback.class);

    public void callBack(WatchEvent.Kind<?> kind, String path);

    /**
     * 将XML转为指定的POJO
     *
     * @param clazz
     * @param xmlStr
     * @return
     * @throws Exception
     */
    public static Object xmlStrToOject(Class<?> clazz, String xmlStr) throws Exception {
        Object xmlObject = null;
        Reader reader = null;
        JAXBContext context = JAXBContext.newInstance(clazz);

        // XML 转为对象的接口
        Unmarshaller unmarshaller = context.createUnmarshaller();

        reader = new StringReader(xmlStr);
        xmlObject = unmarshaller.unmarshal(reader);

        reader.close();

        return xmlObject;
    }

    public static void load(Class clazz,String configLocation){
        SAXReader reader = new SAXReader();
        reader.setEncoding("UTF-8");
        //获得文档对象模型
        InputStream inputStream = null;
        try {
            File file = new File(configLocation);
            inputStream = new FileInputStream(file);
            Document doc = reader.read(new InputStreamReader(inputStream,"UTF-8"));
            //获得根元素
            Element root = doc.getRootElement();
            List<Element> elements = root.elements("bean");
            if(elements!=null){
                for (Element nodes: elements) {
                    String id =nodes.attributeValue("id");
                    String classPath =nodes.attributeValue("class");
                    if (clazz.getClass().getName().equals(classPath)){
                        List<Element> tasks =  nodes.elements("field");
                        if(tasks!=null){
                            for (Element task: tasks) {
                                String name = task.attributeValue("name");
                                String value = task.attributeValue("value");
                                String explain = task.attributeValue("explain");
                                BeanUtils.setProperty(clazz, name, value);
                            }
                        }else{
                            logger.error("beans节点下面找不到字节点field");
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            logger.error("初始化配置失败",e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        if(logger.isDebugEnabled()){
            logger.info("初始化调度配置文件信息完毕.{}");
        }
    }
}
