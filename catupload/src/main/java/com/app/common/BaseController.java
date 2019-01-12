package com.app.common;

import com.app.pojo.FileInfo;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BaseController {
    protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);
    protected static String UPLOADED_FOLDER = "D://temp//";
    private static String SERVER_DIR_URL = "http://127.0.0.1:9003/remote/upload";

    private static String BOUNDARY = "--------------------";

    /**
     * HttpClient 进行文件传输到远程接口
     * Multipart/form-data
     * @param fileInfo
     */
    public void doRemotePutFile(FileInfo fileInfo){
        File file = new File(fileInfo.getPath());
        PostMethod filePost = new PostMethod(SERVER_DIR_URL);
        logger.info("远程接口地址:{},文件信息:{}",SERVER_DIR_URL,fileInfo);
        try {
            Part[] parts = { new FilePart("file", file) };
            filePost.setRequestEntity(new MultipartRequestEntity(parts,filePost.getParams()));
            HttpClient clients = new HttpClient();
            int status = clients.executeMethod(filePost);
            logger.info("接口:{}请求状态码：{}",new Object[]{SERVER_DIR_URL,status});
            BufferedReader rd = new BufferedReader(new InputStreamReader(filePost.getResponseBodyAsStream(), "UTF-8"));
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();
            logger.info("接受到的流是：" + stringBuffer + "—-" + status);
        } catch (Exception e) {
            logger.error("传输文件接口出错", e);
        }
    }


    public void doRemotePutFileByUrlConnection(FileInfo fileInfo){
        File file = new File(fileInfo.getPath());
        if (file == null) return;
        try{
            URL url = new URL(SERVER_DIR_URL);
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
            out.writeBytes("Content-Disposition: form-data;name=\"file\";filename=\"" + fileInfo.getFileName() + "\"\r\n");
            out.writeBytes("Content-Type:application/octet-stream\r\n\r\n");

            //将数据以字节流形式写入
            InputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int length = -1;
            while((length = inputStream.read(buffer)) != -1){
                out.write(buffer, 0, length);
            }
            out.writeBytes(BOUNDARY);
            inputStream.close();
            out.flush();

            //获取URL的响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuffer s = new StringBuffer();
            String temp = "";
            while((temp = reader.readLine()) != null){
                s.append(temp);
            }
            reader.close();
            out.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        BaseController baseController = new BaseController();
        FileInfo fileInfo = new FileInfo();
        fileInfo.setPath("D:\\mypicture\\IMG_0128.JPG");
        fileInfo.setFileName("apache");
        baseController.doRemotePutFile(fileInfo);
       // baseController.test1();
        //useNormalIO();
       // useFileChannel();
    }

    public void test1(){
        PostMethod filePost = new PostMethod("http://127.0.0.1:9003/remote/upload");
        HttpClient client = new HttpClient();
        try {
            int status = client.executeMethod(filePost);
            String cxt = filePost.getResponseBodyAsString();
            logger.info(String.valueOf(status));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void useNormalIO() throws Exception {
        File file = new File("D:\\Download\\告别单身BD1280高清中字.mp4");
        File oFile = new File("D:\\Download\\告别单身BD1280高清中字");

        long time1 = System.currentTimeMillis();
        InputStream is = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(oFile);
        byte[] buf = new byte[64 * 1024];
        int len = 0;
        while((len = is.read(buf)) != -1) {
            fos.write(buf, 0, len);
        }
        fos.flush();
        fos.close();
        is.close();
        long time2 = System.currentTimeMillis();
        System.out.println("Time taken: "+(time2-time1)+" ms");
    }

    private static void useFileChannel() throws Exception {
        File file = new File("D:\\Download\\告别单身BD1280高清中字.mp4");
        File oFile = new File("D:\\Download\\告别单身BD1280高清中字");

        long time1 = System.currentTimeMillis();
        FileInputStream is = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(oFile);
        FileChannel f = is.getChannel();
        FileChannel f2 = fos.getChannel();

        ByteBuffer buf = ByteBuffer.allocateDirect(64 * 1024);
        long len = 0;
        while((len = f.read(buf)) != -1) {
            buf.flip();
            f2.write(buf);
            buf.clear();
        }

        f2.close();
        f.close();

        long time2 = System.currentTimeMillis();
        System.out.println("Time taken: "+(time2-time1)+" ms");
    }

}
