package com.bobo.serializer.impl;

import com.bobo.base.CatException;
import com.bobo.enums.CEnum;
import com.bobo.serializer.CObjectSerializer;

import java.io.*;

public class CJavaSerializer implements CObjectSerializer {
    @Override
    public byte[] serialize(final Object obj) throws Exception {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ObjectOutput output = new ObjectOutputStream(stream);
            output.writeObject(obj);
            output.flush();
            return stream.toByteArray() ;
        }catch (IOException e){
            throw e;
        }
    }

    @Override
    public <T> T deSerialize(byte[] param, Class<T> clazz) throws CatException {
        try (
            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(param);
            ObjectInput input = new ObjectInputStream(arrayInputStream)) {
            return (T) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new CatException("java 序列化错误", e);
        }
    }

    @Override
    public String getScheme() {
        return CEnum.JDK.getSerialize();
    }
}
