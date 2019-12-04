package com.app.ftp.impl;

import com.app.ftp.IFTPClient;
import com.app.ftp.FTPConfig;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ProtocolCommandListener;
import org.apache.commons.net.ftp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Objects;
import java.util.StringTokenizer;


/**
 * Created by 寿继伟 on 2018/12/24.
 * commons-net 3.6 低于 3.6 版本有bug list 文件对象会过滤掉部分文件 
 */
public class DefaultFTPClient implements IFTPClient {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	protected final FTPConfig ftpconfig ;
	protected final FTPClient ftpClient;
	protected String SERVER_CHARSET = "ISO-8859-1";
	protected String LOCAL_CHARSET = "GBK";
	protected String FTPEnvironment = null;//服务器类型 
	protected boolean debugListener = false;
	protected final ProtocolCommandListener  default_listener = new FTPCommandListener();
	
	public DefaultFTPClient(FTPConfig config) {
		this.ftpClient = new FTPClient();
        this.ftpconfig = config;
        ftpClient.setConnectTimeout(ftpconfig.getClientTimeout());
    	ftpClient.setBufferSize(ftpconfig.getBufferSize());
	}
	
    public DefaultFTPClient(FTPConfig config,boolean debug) {
    	this(config);
    	if(debug){
    		debugListener = debug;
    		ftpClient.addProtocolCommandListener(default_listener);
    	}
    }
    public DefaultFTPClient(FTPConfig config,ProtocolCommandListener listener) {
    	this(config);
    	if(listener!=null){
    		ftpClient.addProtocolCommandListener(listener);
    	}
    }
    /**
     * 客户端转服务端编码
     * @param clientStr
     * @return
     */
    @Override
    public String clientStrToServer(String clientStr) {
        try {
            return new String(clientStr.getBytes(LOCAL_CHARSET), SERVER_CHARSET);
        } catch (UnsupportedEncodingException e) {
        }
        return clientStr;
    }
    
    @Override
    public String serverStrToClient(String serverStr) {
        try {
            return new String(serverStr.getBytes(SERVER_CHARSET), LOCAL_CHARSET);
        } catch (UnsupportedEncodingException e) {
        }
        return serverStr;
    }
    /**
     * 下载ftp 文件路径
     * @param remotePath    ftp文件路径
     * @return 成功byte数组,失败返回null
     */
    @Override
    public byte[] downloadFtpFileToByte(String remotePath){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            boolean download_status = downloadFtpFile(remotePath,byteArrayOutputStream);
            if(byteArrayOutputStream==null || download_status==false){
                return null;
            }
            return byteArrayOutputStream.toByteArray();
        } finally {
            IOUtils.closeQuietly(byteArrayOutputStream);
        }
    }

    /***
     * 下载ftp 文件到本地路径
     * @param remotePath    ftp路径含文件名
     * @param localPath 本地路径
     * @return 成功返回true,失败返回false
     */
    @Override
    public boolean downloadFtpFile(String remotePath,String localPath){
        return downloadFtpFile(remotePath,new File(localPath));
    }

    /**
     * 下载ftp文件夹到本地路径
     * @param remotePath    ftp路径含文件名
     * @param localFile 本地路径的file 对象
     * @return 成功返回true,失败返回false
     */
    @Override
    public boolean downloadFtpFile(String remotePath,File localFile){
        File parent = localFile.getParentFile();
        if(!parent.exists()){
            parent.mkdirs();
        }
        OutputStream  os = null;
        try {
            os = new FileOutputStream(localFile);
            return downloadFtpFile(remotePath,os);
        } catch (IOException e) {
            logger.error("",e);
        }finally {
            IOUtils.closeQuietly(os);
        }
        return false;
    }
    
    /**
     * 下载文件到 OutputStream 对象
     * @param remotePath ftp 文件路径
     * @param output ftp文件到 OutputStream 对象
     * @return 成功返回true,失败返回false
     */
    @Override
    public boolean downloadFtpFile(String remotePath,OutputStream output){
    	return _downloadFtpFile(remotePath, output, true);
    }
    
    private boolean _downloadFtpFile(String remotePath,OutputStream output,boolean isswitch){
    	if(isswitch){
    		remotePath = switchingPath(remotePath);
    	}
        InputStream inputStream = null;
        try {
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
           /* int i = remotePath.lastIndexOf(ftpconfig.getFtpLineSeparator());
            if(i!=-1){
                String ftppath = remotePath.substring(0,i+1);
                boolean b = _makeDirectory(ftppath,false);//250
                logger.debug("下载文件：{} ,切换目录：{} ,切换结果:",remotePath,ftppath,b);
            }*/
            return ftpClient.retrieveFile(clientStrToServer(remotePath),output);
        } catch (IOException e) {
            logger.error("",e);
        }finally {
            IOUtils.closeQuietly(inputStream);
        }
        return false;
    }

    
 

	
    /***
     * 获取本地路径
     * @return
     * @throws IOException
     */
    @Override
    public String pwd() {
		try {
			String pwd = ftpClient.printWorkingDirectory();
			return serverStrToClient(pwd);
		} catch (IOException e) {
			logger.error("",e);
		}
        return null;
    }

    /**
     * 切换到指定路径
     * @param pathname  欲切换的目录
     * @return
     * @throws IOException
     */
    @Override
    public boolean changeWorkingDirectory(String pathname) {
		return _changeWorkingDirectory(pathname,true);
    }
    
    private boolean _changeWorkingDirectory(String pathname,boolean isswitch){
    	if(isswitch){
    		pathname = switchingPath(pathname);
    	}
    	try {
			return this.ftpClient.changeWorkingDirectory(clientStrToServer(pathname));
		} catch (IOException e) {
			return false;
		}
    }

    /**
     * 整理ftp 路径
     * @param path
     * @return
     */
    private String switchingPath(String path){
        path = path.replace(ftpconfig.getObliqueLine(),ftpconfig.getFtpLineSeparator());
        StringTokenizer s = new StringTokenizer(path, ftpconfig.getFtpLineSeparator()); //sign
        StringBuffer pathName = new StringBuffer();
        while (s.hasMoreElements()) {
            Object element = s.nextElement();
            if(element!= null && !"".equals(String.valueOf(element).trim())){
                pathName.append(ftpconfig.getFtpLineSeparator()).append(element);
            }
        }
        if(path.endsWith(ftpconfig.getFtpLineSeparator())){
            pathName.append(ftpconfig.getFtpLineSeparator());
        }
        if(path.startsWith(ftpconfig.getFtpLineSeparator())){
            return pathName.toString();
        }else{
            if(pathName.length()>0){
                return pathName.toString().substring(1);
            }else{
                return pathName.toString();
            }
        }
    }

    /**
     * 切换到Root目录
     * @return
     */
    @Override
    public boolean changeToRootDirectory() {
        try {
        	return ftpClient.changeWorkingDirectory(ftpconfig.getFtpLineSeparator());
        } catch (IOException e) {
            logger.error("",e);
        }
        return false;
    }
    @Override
    public boolean changeToParentDirectory(){
    	try {
			return ftpClient.changeToParentDirectory();
		} catch (IOException e) {
			logger.error("",e);
		}
    	return false;
    }
    @Override
    public boolean login(){
        return  login(ftpconfig.getHost(),Integer.valueOf(ftpconfig.getPort()),ftpconfig.getUsername(),ftpconfig.getPassword());
    }
    @Override
    public String getFTPEnvironment() {
		return FTPEnvironment;
	}
    @Override
    public void logout(){
    	FTPEnvironment = null;
        if (ftpClient!=null) {
            try {
                ftpClient.logout();
            } catch (Exception e) {}
            try {
                ftpClient.disconnect();
            } catch (Exception e) {}
        }
    }
    @Override
    public boolean isLogin(){
    	if(ftpClient!=null){
    		return ftpClient.isConnected();
    	}
    	return false;
    }
    @Override
    public boolean login(String ip,int port,String username,String password){
        try {
            ftpClient.connect(ip,port);
            ftpClient.login(username, password);
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                return false;//账号或密码错误
            }
            FTPEnvironment = ftpClient.getSystemType();
            reply = ftpClient.sendCommand("OPTS UTF8", "ON");
            if (FTPReply.isPositiveCompletion(reply)){// 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
                LOCAL_CHARSET = "UTF-8";
            }
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);// 设置传输的模式
//            ftpClient.enterLocalPassiveMode();// 设置被动模式
            
            if(ftpClient.pasv()==FTPReply.ENTERING_PASSIVE_MODE){
        		ftpClient.enterLocalPassiveMode();	
        	}else{
        		ftpClient.enterLocalActiveMode();            		
        	}
            //logger.info("本地编码:{}",LOCAL_CHARSET);
            boolean b = ftpClient.changeWorkingDirectory("/");
            if(b){
                ftpconfig.setFtpLineSeparator("/");
            }else{
                ftpconfig.setFtpLineSeparator("\\");
            }
        } catch (IOException e) {//服务器尚未开启ftp
            return false;
        }
        return true;
    }

    /**
     * 心跳检测
     * @return
     */
    @Override
    public boolean sendNoOp() {
        try {
            return ftpClient.sendNoOp();
        } catch (IOException e) {
            return false;
        }
    }
    
    private  FTPFile[]  _listFile(String ftpPath,boolean isswitch,FTPFileFilter filter){
        try {
            if(isswitch){
                ftpPath =  switchingPath(ftpPath);
            }
            return ftpClient.listFiles(clientStrToServer(ftpPath),filter);
        } catch (IOException e) {
            logger.error("",e);
        }
        return null;
    }
    @Override
    public FTPFile[] listFiles(String ftpPath,FTPFileFilter filter){
    	return _listFile(ftpPath,true,filter);
    }
    @Override
    public FTPFile[]  listAllFile(String ftpPath){
        return _listFile(ftpPath,true,FTPFileFilters.NON_NULL);
    }
    @Override
    public boolean uploadFileByLocalFile(String remoteFile,String localFilePath){
        return uploadFileByLocalFile(remoteFile,new File(localFilePath));
    }
    @Override
    public boolean uploadFileByLocalFile(String remoteFile,File localFile){
        InputStream inputStream  = null;
        try {
            inputStream = new FileInputStream(localFile);
            return uploadFile(remoteFile,inputStream);
        } catch (FileNotFoundException e) {
            logger.error("文件不存在 {}",localFile.getAbsolutePath(),e);
        }finally {
            IOUtils.closeQuietly(inputStream);
        }
        return false;
    }
    @Override
    public boolean uploadFileByBytes(String remoteFile,byte[] bytes){
        InputStream inputStream = new ByteArrayInputStream(bytes);
        try {
            return uploadFile(remoteFile,inputStream);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
    @Override
    public boolean uploadFile(String remoteFile,InputStream inputStream){
        try {
            this.ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            String remftp = switchingPath(remoteFile);
            int index = remftp.lastIndexOf(ftpconfig.getFtpLineSeparator());
            if(index!=-1) {
                String ftppath = remftp.substring(0, index + 1);
                _makeDirectory(ftppath,false);
            }
            return ftpClient.storeFile(clientStrToServer(remftp),inputStream);
        } catch (IOException e) {
            logger.error("",e);
        }
        return false;
    }
    
    
    @Override
    public boolean deleteDirectory(String dir){
        return _deleteDirectory(dir,true);
    }
    
    private boolean _deleteDirectory(String dir,boolean isswitch){
        if(isswitch){
            dir = switchingPath(dir);
            if(dir.endsWith(ftpconfig.getFtpLineSeparator())){
                dir = dir.substring(0,dir.length()-1);
            }
        }
        FTPFile[] ftpFiles = _listFile(dir,false,FTPFileFilters.NON_NULL);
        if(ftpFiles!=null){
            for (int i = 0; i < ftpFiles.length; i++) {
                FTPFile ftpFile = ftpFiles[i];
                String ftpname = serverStrToClient(ftpFile.getName());
                if(ftpFile.isDirectory()){
                	 _deleteDirectory(dir+ftpconfig.getFtpLineSeparator()+ftpname,false);
                }else{
                	boolean status = _deleteFile(dir+ftpconfig.getFtpLineSeparator()+ftpname,false);
                }
            }
        }
        try {
        	boolean status = ftpClient.removeDirectory(clientStrToServer(dir));
            return status;
        } catch (IOException e) {
            logger.error("",e);
        }
        return false;
    }
    
    @Override
    public boolean deleteFile(String remotePath){
        return _deleteFile(remotePath,true);
    }

    /**
     * 删除文件
     * @param remotePath
     * @param isswitch  对外采用true,对内采用false
     * @return
     */
    private boolean _deleteFile(String remotePath,boolean isswitch){
        if(isswitch){
            remotePath = switchingPath(remotePath);
        }
        try {
            return ftpClient.deleteFile(clientStrToServer(remotePath));
        } catch (IOException e) {
            logger.error("",e);
        }
        return false;
    }
    
 
    @Override
    public String remotehelp(){
		try {
			int reply = ftpClient.sendCommand("remotehelp");
			if(FTPReply.isPositiveCompletion(reply)){
				return ftpClient.getReplyString();
			}
		} catch (IOException e) {
			logger.error("",e);
		}
    	return null;
    }
    /***
     * 验证目录是否存在
     * @param path	目录路径
     * @return	存在返回true
     */
    @Override
    public boolean checkDirectory(String path) {
        path = switchingPath(path);
        boolean exist = false;
        try {
            if (Objects.nonNull(path) && !path.isEmpty()) {
                String sourcePath = pwd();
                this.ftpClient.changeWorkingDirectory(clientStrToServer(path));
                this.ftpClient.changeWorkingDirectory(clientStrToServer(sourcePath));
            }
            exist = true;
        } catch (IOException e) {
            logger.error("",e);
            exist = false;
        }
        return exist;
    }
    @Override
    public boolean makeDirectory(String remotePath){
        return _makeDirectory(remotePath,true);
    }

    private boolean _makeDirectory(String remotePath,boolean isswitch){
        if(isswitch){
            remotePath = switchingPath(remotePath);
        }
        return __makeDirectorys(remotePath);
    }
    private boolean __makeDirectorys(String remote){
    	StringTokenizer s = new StringTokenizer(remote, ftpconfig.getFtpLineSeparator()); //sign
        StringBuffer pathName = new StringBuffer();
        boolean status  = false;
        while (s.hasMoreElements()) {
            Object element = s.nextElement();
            if(element!= null && !"".equals(String.valueOf(element).trim())){
                pathName.append(ftpconfig.getFtpLineSeparator()).append(element);
                try {
                	String c = clientStrToServer(pathName.toString());
                	status = ftpClient.makeDirectory(c);
				} catch (IOException e) {
					logger.error("",e);
				}
            }
        }
        return status;
    }
    
    @Override
    public boolean rename(String from, String to){
        return _rename(from,to,true);
    }
    /***
     * 重命名 ftp 服务器 文件或文件夹
     * @param from 原名
     * @param to	新名
     * @throws IOException
     */
    private boolean _rename(String from, String to,boolean isswitch)  {
        try {
            if(isswitch){
                from = switchingPath(from);
                to = switchingPath(to);
            }
            return ftpClient.rename(clientStrToServer(from), clientStrToServer(to));
        } catch (IOException e) {
            logger.error("",e);
        }
        return false;
    }
    
    @Override
    public boolean isCommandDebug(){
    	return debugListener;
    }
    @Override
    public void openDefaultCommandDebug(){
    	ftpClient.addProtocolCommandListener(default_listener);
    	debugListener  = true;
    }
    @Override
    public void removeDefaultCommandDebug(){
    	ftpClient.removeProtocolCommandListener(default_listener);
    	debugListener  = false;
    }
    
	
	@Override
	public void enterLocalActiveMode() {
		ftpClient.enterLocalActiveMode();
	}

	@Override
	public void enterLocalPassiveMode() {
		ftpClient.enterLocalPassiveMode();
	}

	@Override
	public boolean setFileType(int fileType) {
		try {
			return ftpClient.setFileType(fileType);
		} catch (IOException e) {
			return false;
		}
	}


	@Override
	public boolean completePendingCommand() {
		try {
			return ftpClient.completePendingCommand();
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public int getReplyCode() {
		return ftpClient.getReplyCode();
	}

	@Override
	public String getReplyString() {
		return ftpClient.getReplyString();
	}

	@Override
	public int sendCommand(FTPCmd command) throws IOException {
		return ftpClient.sendCommand(command);
	}

	@Override
	public int sendCommand(FTPCmd command, String args) throws IOException {
		return ftpClient.sendCommand(command,args);
	}

	@Override
	public int sendCommand(String command) throws IOException {
		return ftpClient.sendCommand(command);
	}
	
	@Override
	public  int sendCommand(String command, String args) throws IOException{
		return ftpClient.sendCommand(command,args);
	}

	
}
