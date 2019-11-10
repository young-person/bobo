package com;

import com.app.pojo.Cat;
import com.app.pojo.Property;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.util.ResourceUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
/**
 * 将微服务注册到Eureka Server服务中心 使用fetch
 */
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class CatwebserviceApplication {

    public static void main(String[] args) throws Exception {
        loadCatConfig();
        SpringApplication.run(CatwebserviceApplication.class, args);
    }


    public static final Map<String, Property> CAT_CACHE = new ConcurrentHashMap<>();
    /**
     * 加载配置文件
     */
    private static void loadCatConfig() {
        String path = "classpath:cat.xml";
        try {
            File file = ResourceUtils.getFile(path);
            JAXBContext context = JAXBContext.newInstance(Cat.class);
            Unmarshaller unMar = context.createUnmarshaller();
            Cat cat = (Cat) unMar.unmarshal(file);
            List<Property> list = cat.getProperties();
            for (Property property : list) {
                CAT_CACHE.put(property.getName(), property);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
