package com.app.crawler.base;

import com.app.crawler.pojo.Cat;
import com.app.crawler.pojo.Property;
import com.app.crawler.url.Linking;
import com.bobo.base.BaseClass;
import org.springframework.util.ResourceUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 缓存类
 * @author user
 *
 */
public class RCache extends BaseClass {
    /**
     * 配置缓存
     */
    public static final Map<String, Property> CAT_CACHE = new ConcurrentHashMap<>();
    
    private String path;

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 加载配置文件
     */
    public void loadCatConfig() {
    	StringBuilder builder = new StringBuilder(path);
    	builder.append(Linking.SYSTEMPATH.getUrl());
    	builder.append("cat.xml");
        try {
            LOGGER.debug("开始加载配置文件:{}",builder.toString());
            File file = new File(builder.toString());
            if (!file.exists()) {
				file = ResourceUtils.getFile(path);
			}
            JAXBContext context = JAXBContext.newInstance(Cat.class);
            Unmarshaller unMar = context.createUnmarshaller();
            Cat cat = (Cat) unMar.unmarshal(file);
            List<Property> list = cat.getProperties();
            for (Property property : list) {
                CAT_CACHE.put(property.getName(), property);
                LOGGER.debug("加载配置项key：【】---》value：【】", property.getName(), property);
            }
            LOGGER.debug("加载配置文件完毕，配置项数量：【{}】", CAT_CACHE.size());
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
