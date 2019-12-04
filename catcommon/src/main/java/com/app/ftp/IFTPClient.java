package com.app.ftp;

import org.apache.commons.net.ftp.FTPCmd;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * Created by 寿继伟 on 2018/12/24.
 */
public interface IFTPClient {

	// 主动
	void enterLocalActiveMode();
	// 被动
	void enterLocalPassiveMode();
	// 设置数据类型 FTP.
	boolean setFileType(int fileType);
	
	boolean completePendingCommand();
	
	int getReplyCode();
	
	String getReplyString();
	
	int sendCommand(FTPCmd command)  throws IOException;
	
	int sendCommand(FTPCmd command, String args)  throws IOException;
	
	int sendCommand(String command) throws IOException;
	
	int sendCommand(String command, String args) throws IOException;
    /**
     * 客户端转服务端 
     * @param clientStr
     * @return
     */
    String clientStrToServer(String clientStr);
    
    /**
     * 服务端转客户端
     * @param serverStr
     * @return
     */
    String serverStrToClient(String serverStr);

    /**
     * 下载ftp 文件路径
     * @param remotePath    ftp文件路径
     * @return 成功byte数组,失败返回null
     */
    byte[] downloadFtpFileToByte(String remotePath);

    /***
     * 下载ftp 文件到本地路径
     * @param remotePath    ftp路径含文件名
     * @param localPath 本地路径
     * @return 成功返回true,失败返回false
     */
    boolean downloadFtpFile(String remotePath, String localPath);

    /**
     * 下载ftp文件夹到本地路径
     * @param remotePath    ftp路径含文件名
     * @param localFile 本地路径的file 对象
     * @return 成功返回true,失败返回false
     */
    boolean downloadFtpFile(String remotePath, File localFile);

    /**
     * 下载文件到 OutputStream 对象
     * @param remotePath ftp 文件路径
     * @param output ftp文件到 OutputStream 对象
     * @return 成功返回true,失败返回false
     */
    boolean downloadFtpFile(String remotePath, OutputStream output);

    

    /***
     * 获取本地路径
     * @return
     */
    String pwd() ;

    /**
     * 切换到指定路径
     * @param pathname  欲切换的目录
     * @return
     */
    boolean changeWorkingDirectory(String pathname);

   
    /**
     * 切换到Root目录
     * @return
     */
    boolean changeToRootDirectory() ;
    
    /***
     * 切换到上级目录
     * @return
     */
    boolean changeToParentDirectory();
    
    /**
     * 登录FTP
     * @return
     */
    boolean login();
    
    /**
     * 登录ftp 
     * @param ip	地址
     * @param port	端口
     * @param username	账号
     * @param password	密码
     * @return
     */
    boolean login(String ip, int port, String username, String password);
    
    /***
     * 服务器类型
     * @return
     */
    String getFTPEnvironment();
    
    /**
     * 退出FTP
     */
    void logout();
    
    /**
     * 是否登录
     * @return
     */
    boolean isLogin();

    /**
     * 心跳检测
     * @return
     */
    boolean sendNoOp();
    
 
    
    /**
     * 上传本地文件到FTP远程路径
     * @param remoteFile 远程路径含文件名
     * @param localFilePath	本地路径含文件名
     * @return
     */
    boolean uploadFileByLocalFile(String remoteFile, String localFilePath);
    
    /***
     * 上传本地文件对象到FTP远程路径
     * @param remoteFile 远程路径含文件名
     * @param localFile 本地文件对象 
     * @return
     */
    boolean uploadFileByLocalFile(String remoteFile, File localFile);
    
    /***
     * 上传二进制流文件对象到远程路径
     * @param remoteFile	远程路径含文件名
     * @param bytes	二进制文件对象
     * @return
     */
    boolean uploadFileByBytes(String remoteFile, byte[] bytes);
    
    /***
     * 上传输入流到远程路径
     * @param remoteFile	远程路径含文件名
     * @param inputStream	文件输入流
     * @return
     */
    boolean uploadFile(String remoteFile, InputStream inputStream);
    

    
    /**
     * 删除文件夹 以及文件夹内所有文件
     * @param dir 远程目录
     * @return
     */
    boolean deleteDirectory(String dir);
    
   
    
    /***
     *	删除远程文件 
     * @param remotePath	远程文件路径
     * @return
     */
    boolean deleteFile(String remotePath);
    
    /**
     * 远程服务器 所支持的命令
     * @return
     */
    String remotehelp();
    
    /***
     * 验证目录是否存在
     * @param path	目录路径
     * @return	存在返回true
     */
    boolean checkDirectory(String path);
    
    /***
     * 创建远程目录
     * @param remotePath
     * @return
     */
    boolean makeDirectory(String remotePath);
    
    /**
     * 重命名远程文件名 或 目录
     * @param from	远程原文件名或目录
     * @param to	欲修改的远程文件名或目录
     * @return
     */
    boolean rename(String from, String to);

    
    /**
     * 是否开启默认协议命令监听
     * @return
     */
    boolean isCommandDebug();
    
    /***
     * 开启默认认协议命令监听
     */
    void openDefaultCommandDebug();
    
    /***
     * 移除默认协议命令监听
     */
    void removeDefaultCommandDebug();

    /**
     * 查看文件列表
     * @param ftpPath
     * @param filter FTPFileFilters 过滤
     * @return
     */
    FTPFile[] listFiles(String ftpPath, FTPFileFilter filter);
    
    /**
     * 查看列表	
     * @param ftpPath
     * @return
     */
    FTPFile[]  listAllFile(String ftpPath);
}
