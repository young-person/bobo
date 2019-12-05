package com.app.ftp;

import com.app.ftp.impl.DefaultFTPClient;

import java.io.File;

public class FtpTest {

    public static void main(String[] args) {
        FTPConfig config = new FTPConfig("129.28.191.240",21,"bobo","czb199345");
        IFTPClient client = new DefaultFTPClient(config,true);
        client.login();
        boolean s = client.uploadFileByLocalFile("/nginx-1.12.2.zip",new File("D:\\env\\nginx-1.12.2.zip"));
        System.out.println(s);
        client.logout();
    }
}
