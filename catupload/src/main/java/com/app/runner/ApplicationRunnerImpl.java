package com.app.runner;

import com.app.config.CatUploadProperty;
import com.app.pojo.Cat;
import com.app.pojo.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationRunnerImpl.class);
    @Autowired
    private CatUploadProperty catWebServiceProperty;

    /**
     * 配置缓存
     */
    public static final Map<String, Property> CAT_CACHE = new ConcurrentHashMap<>();
    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.loadCatConfig();
    }

    /**
     * 加载配置文件
     */
    private void loadCatConfig() {
        try {
            LOGGER.debug("开始加载配置文件");
            File file = ResourceUtils.getFile(catWebServiceProperty.getCollocation());
            JAXBContext context = JAXBContext.newInstance(Cat.class);
            Unmarshaller unMar = context.createUnmarshaller();
            Cat cat = (Cat) unMar.unmarshal(file);
            List<Property> list = cat.getProperties();
            for (Property property : list) {
                CAT_CACHE.put(property.getName(), property);
                LOGGER.debug("加载配置项key：【】---》value：【】", property.getName(), property);
            }
            LOGGER.debug("加载配置文件完毕，配置项数量：【{}】", CAT_CACHE.size());

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
