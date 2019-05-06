package com.test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mybatis.pojo.China;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

public class GjsonTest {

    public static void main(String[] args) {
        Gson gson = new Gson();
        int num = gson.fromJson("100", int.class);
        String json = "{\"success\":false, \"message\":\"can't create new file!\"}";
        Map<String,String> m = gson.fromJson(json, Map.class);
        System.out.printf(m.toString());
        JsonObject o = new JsonObject();

        China china = new China();
        try {
//            PropertyUtils.setNestedProperty(china,"bpbp","bobo");
//            PropertyUtils.setIndexedProperty(china,"cccc","cccc");
//            PropertyUtils.setMappedProperty(china,"cccc","cccc");
//            PropertyUtils.setSimpleProperty(china,"cccc","cccc");
            PropertyUtils.setProperty(china,"cccc","cccc");
            System.out.printf(china.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }





    @Test
    public void test(){
        String BOUNDARY = "--------------------";

        String path = "D:\\environment\\VMware-workstation-full-14.1.3-9474260.exe";

        try {

            URL url = new URL("http://127.0.0.1:9008/remote/upload");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设置请求头
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            connection.setRequestProperty("Charsert", "UTF-8");
            connection.setRequestProperty("Content-Type","multipart/form-data; boundary=" + BOUNDARY);

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(BOUNDARY);
            out.writeBytes("\n");
            out.writeBytes("Content-Disposition: form-data;name=\"file\";filename=xxxxxxxxxxxx");
            out.writeBytes("Content-Type:application/octet-stream\r\n\r\n");

//            ByteArrayOutputStream os1 = new ByteArrayOutputStream();

            InputStream inputStream = null;
            inputStream = new FileInputStream(path);

            byte[] byteBuf = new byte[3*1000];
            byte[] base64ByteBuf;
            int count1; //每次从文件中读取到的有效字节数
            while((count1=inputStream.read(byteBuf)) != -1)
            {
                if(count1!=byteBuf.length) //如果有效字节数不为3*1000，则说明文件已经读到尾了，不够填充满byteBuf了
                {
                    byte[] copy = Arrays.copyOf(byteBuf, count1); //从byteBuf中截取包含有效字节数的字节段
                    base64ByteBuf = Base64.encodeBase64(copy); //对有效字节段进行编码
                }
                else
                {
                    base64ByteBuf = Base64.encodeBase64(byteBuf);
                }
                out.write(base64ByteBuf, 0, base64ByteBuf.length);
                out.flush();
            }
            inputStream.close();
//            System.out.println(out.toString());
//            //获取URL的响应
//            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
//            StringBuffer s = new StringBuffer();
//            String temp = "";
//            while((temp = reader.readLine()) != null){
//                s.append(temp);
//            }
//            reader.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
