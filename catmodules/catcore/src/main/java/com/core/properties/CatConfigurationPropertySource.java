package com.core.properties;

import org.apache.commons.beanutils.BeanUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Properties;

public abstract class CatConfigurationPropertySource<E>{

    private static final String BIND = "cat.cfg";

    private Class<E> e;

    @SuppressWarnings("unchecked")
	public CatConfigurationPropertySource(){
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        e = (Class<E>) actualTypeArguments[0];
        String name = getSimpleName(e.getSimpleName());

        Properties properties = getClassProperties(name);

        try {
            autoPropertiesToBean(properties,name);
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    public String loadConfigurationPath(){
        return null;
    }

    private String getSimpleName(String name){
        int index = name.lastIndexOf("Properties");
        if (index > -1){
            name = name.substring(0,index);
        }
        return name.toLowerCase();
    }


    private Properties getClassProperties(String name){
        File files= null;
        if (null != loadConfigurationPath()){
            files = new File(loadConfigurationPath());
        }else{
            String path=e.getClass().getResource("/").getPath();
            files = new File(path+name+".properties");
        }
        Properties properties = new Properties();
        if (files.exists()){
            try {
                properties.load(new FileInputStream(files));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return properties;
    }


    private void autoPropertiesToBean(Properties properties,String name) throws InvocationTargetException, IllegalAccessException {
        Field[]  fields = e.getDeclaredFields();

        for(Object key:properties.keySet()){
            Object val = properties.get(key);
            for(Field field:fields){
                if (key.equals(BIND+"."+name+"."+field.getName())){
                    BeanUtils.copyProperty(this,field.getName(),val);
                }
            }
        }
    }
}
