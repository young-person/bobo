package com.test.socket;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

public class ServerBoot {

    public static void main(String[] args) {

        try {

            ServerSocket socket = new ServerSocket(10000);

            StringBuffer buffer = new StringBuffer();
            while (true) {

                Socket server = socket.accept();
                byte[] bytes = new byte[1024 * 4];
                while (server.getInputStream().read(bytes) != -1) {
                    buffer.append(new String(bytes));
                }

                String[] split = buffer.toString().split("/r/n");
                String packageName = split[0];
                String className = split[1];
                String method = split[2];
                String argVal = split[3];

                Class<?> classObj = null;
                try {
//                获取的包和类名找到本地的利用反射实例化
                    classObj = Class.forName(packageName + "." + className);
                    Method returnMethod = classObj.getMethod(method, String.class);
                    String result = (String) returnMethod.invoke(classObj.newInstance(), argVal);
                    System.out.println("返回给客户端的结果:" + result);
                    server.getOutputStream().write(result.getBytes(Charset.forName("UTF-8")));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}