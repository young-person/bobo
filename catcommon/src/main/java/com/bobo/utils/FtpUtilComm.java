package com.bobo.utils;

import com.app.ftp.FTPConfig;
import com.bobo.base.BaseClass;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.SocketException;


public class FtpUtilComm extends  BaseClass{

	private FTPClient ftpClient = new FTPClient();
	private FTPConfig config;

	public FTPConfig getConfig() {
		return config;
	}

	public void setConfig(FTPConfig config) {
		this.config = config;
	}

	public FtpUtilComm(FTPConfig config) {
		this.config = config;
	}

	public FTPClient getFtpClient() {
		return this.ftpClient;
	}

	public boolean connect() throws IOException {
		this.ftpClient.connect(this.config.getHost(),Integer.valueOf(this.config.getPort()));
		this.ftpClient.setControlEncoding("UTF-8");
		if ((FTPReply.isPositiveCompletion(this.ftpClient.getReplyCode()))
				&& (this.ftpClient.login(this.config.getUsername(),
						this.config.getPassword()))) {
			return true;
		}

		disconnect();
		return false;
	}

	/**
	 * apache的ftp工具类，最好是先logout，再最后disconnect
	 * @throws IOException
	 */
	public void disconnect() {
		if (this.ftpClient.isConnected()) {
			try {
				this.ftpClient.disconnect();
			}catch(Exception e) {
				
			}
		}
	}

	public String pwd() throws IOException {
		String pwd = this.ftpClient.printWorkingDirectory();
		return iso2Gbk(pwd);
	}

	private String gbk2Iso(String str) {
		try {
			return new String(str.getBytes("gb2312"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
		}
		return str;
	}

	private String iso2Gbk(String str) {
		try {
			return new String(str.getBytes("iso-8859-1"), "gb2312");
		} catch (UnsupportedEncodingException e) {
		}
		return str;
	}
	
	public boolean sendNoOp() {
		try {
			return ftpClient.sendNoOp();
		} catch (IOException e) {
			return false;
		}
	}

	private String toLinuxPath(String path) {
		if (path.contains("\\"))
			return path.replace("\\", "/");
		return path;
	}



	public boolean deleteFile(String pathname) throws IOException {
		pathname = gbk2Iso(toLinuxPath(pathname));
		return this.ftpClient.deleteFile(pathname);
	}

	public String upload(String local, String remote) throws IOException {
		this.ftpClient.enterLocalPassiveMode();

		this.ftpClient.setFileType(2);

		String remoteFileName = remote;
		if (remote.contains("/")) {
			remoteFileName = remote.substring(remote.lastIndexOf("/") + 1);

			if ("Create_Directory_Fail".equals(createDirecroty(remote))) {
				return "Create_Directory_Fail";
			}
		}

		String result = uploadFile(remoteFileName, new File(local), 0L);

		if (local.indexOf("picture/") > 0) {
			local = local.replace("picture/", "temp/download/");
			int index = local.lastIndexOf("/");
			local = local.substring(0, index + 1);
			File file_in = new File(local);
			local = file_in.getParent();
		}

		if (local.indexOf("upload/") > 0) {
			local = local.replace("upload/", "temp/download/");
			int index = local.lastIndexOf("/");
			local = local.substring(0, index + 1);
			File file_in = new File(local);
			local = file_in.getParent();
		}

		return result;
	}

	private String createDirecroty(String remote) throws IOException {
		String status = "Create_Directory_Success";
		String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
		if ((!directory.equalsIgnoreCase("/"))
				&& (!this.ftpClient.changeWorkingDirectory(gbk2Iso(directory)))) {
			int start = 0;
			int end = 0;
			if (directory.startsWith("/"))
				start = 1;
			else {
				start = 0;
			}
			end = directory.indexOf("/", start);
			do {
				String subDirectory = new String(remote.substring(start, end)
						.getBytes("GBK"), "iso-8859-1");
				if (!this.ftpClient.changeWorkingDirectory(subDirectory)) {
					if (this.ftpClient.makeDirectory(subDirectory)) {
						this.ftpClient.changeWorkingDirectory(subDirectory);
					} else {
						LOGGER.error("创建目录失败");
						return "Create_Directory_Fail";
					}
				}

				start = end + 1;
				end = directory.indexOf("/", start);
			}

			while (end > start);
		}

		return status;
	}

	private String uploadFile(String remoteFile, File localFile, long remoteSize)
			throws IOException {
		long step = localFile.length() / 100L;
		long process = 0L;
		long localreadbytes = 0L;
		RandomAccessFile raf = new RandomAccessFile(localFile, "r");
		OutputStream out = this.ftpClient.appendFileStream(gbk2Iso(remoteFile));

		if (remoteSize > 0L) {
			this.ftpClient.setRestartOffset(remoteSize);
			process = remoteSize / step;
			raf.seek(remoteSize);
			localreadbytes = remoteSize;
		}
		byte[] bytes = new byte[1024];
		int c = 0;
		while ((c = raf.read(bytes)) != -1) {
			out.write(bytes, 0, c);
			localreadbytes += c;

			if (localreadbytes / step != process) {
				process = localreadbytes / step;
				LOGGER.info("上传进度:" + process);
			}
		}
		out.flush();
		raf.close();
		out.close();
		boolean result = this.ftpClient.completePendingCommand();
		String status = "";
		if (remoteSize > 0L)
			status = result ? "Upload_From_Break_Success"
					: "Upload_From_Break_Failed";
		else {
			status = result ? "Upload_New_File_Success"
					: "Upload_New_File_Failed";
		}
		return status;
	}

	public String upload(InputStream is, String remoteFile) throws IOException {
		this.ftpClient.enterLocalPassiveMode();
		this.ftpClient.setFileType(2);

		OutputStream out = this.ftpClient.appendFileStream(gbk2Iso(remoteFile));
		byte[] bytes = new byte[1024];
		int c = 0;
		while ((c = is.read(bytes)) != -1) {
			out.write(bytes, 0, c);
		}
		out.flush();
		out.close();
		boolean result = this.ftpClient.completePendingCommand();
		String status = result ? "Upload_New_File_Success"
				: "Upload_New_File_Failed";
		return status;
	}
	
	public String download(String remote, String local) throws IOException {
		this.ftpClient.enterLocalPassiveMode();

		this.ftpClient.setFileType(2);

		FTPFile[] files = this.ftpClient.listFiles(gbk2Iso(remote));
		if (files.length != 1) {
			LOGGER.error("远程文件不存在");
			return "Remote_File_Noexist";
		}

		long lRemoteSize = files[0].getSize();
		File f = new File(local);

		boolean bool = f.exists();
		bool = false;
		String result = "";
		if (bool) {
			long localSize = f.length();

			if (localSize >= lRemoteSize) {
				LOGGER.info("本地文件大于远程文件，下载中止");
				return "Local_Bigger_Remote";
			}

			FileOutputStream out = new FileOutputStream(f, true);
			this.ftpClient.setRestartOffset(localSize);
			InputStream in = this.ftpClient.retrieveFileStream(gbk2Iso(remote));
			byte[] bytes = new byte[1024];
			long step = lRemoteSize / 100L;
			long process = localSize / step;
			int c = 0;
			while ((c = in.read(bytes)) != -1) {
				out.write(bytes, 0, c);
				localSize += c;
				long nowProcess = localSize / step;
				if (nowProcess > process) {
					process = nowProcess;
					if (process % 10L == 0L) {
						LOGGER.info("下载进度：" + process);
					}
				}
			}

			in.close();
			out.close();
			boolean isDo = this.ftpClient.completePendingCommand();
			if (isDo)
				result = "Download_From_Break_Success";
			else
				result = "Download_From_Break_Failed";
		} else {
			OutputStream out = new FileOutputStream(f);
			InputStream in = this.ftpClient.retrieveFileStream(gbk2Iso(remote));
			byte[] bytes = new byte[1024];
			long step = lRemoteSize / 100L;
			long process = 0L;
			long localSize = 0L;
			int c = 0;
			while ((c = in.read(bytes)) != -1) {
				out.write(bytes, 0, c);
				localSize += c;
				long nowProcess = localSize / step;
				if (nowProcess > process) {
					process = nowProcess;
					if (process % 10L == 0L) {
						LOGGER.info("下载进度：" + process);
					}
				}
			}
			in.close();
			out.close();
			boolean upNewStatus = this.ftpClient.completePendingCommand();
			if (upNewStatus)
				result = "Download_New_Success";
			else {
				result = "Download_New_Failed";
			}
		}
		return result;
	}

	/**
	 * 
	 * @param remote
	 * @param local
	 * @return
	 */
	public String downloadUp(String remote, String local) {
		String filename = remote;
		String ftppath = "/";
		if(remote.indexOf("/") > -1) {
			int index = remote.lastIndexOf("/");
			filename = remote.substring(index+1);
			ftppath = remote.substring(0, index)+"/";
		}else if(remote.indexOf("\\") > -1){
			int index = remote.lastIndexOf("\\");
			filename = remote.substring(index+1);
			ftppath = remote.substring(0, index)+"/";
		}
		
		String localpath = "";
		String local_filename = "";
		if(local.indexOf("/") > -1) {
			int ind = local.lastIndexOf("/");
			localpath = local.substring(0, ind);
			local_filename = local.substring(ind+1);
		}else if(local.indexOf("\\") > -1){
			int ind = local.lastIndexOf("\\");
			localpath = local.substring(0, ind);
			local_filename = local.substring(ind+1);
		}
		downloadFtpFile(ftppath,localpath,filename,local_filename);
		return "Success";
	}

	public void download(String remote, OutputStream os) throws IOException {
		this.ftpClient.enterLocalPassiveMode();

		this.ftpClient.setFileType(2);

		FTPFile[] files = this.ftpClient.listFiles(gbk2Iso(remote));
		if (files.length != 1) {
			LOGGER.error("远程文件不存在");
			return;
		}

		long lRemoteSize = files[0].getSize();

		InputStream in = this.ftpClient.retrieveFileStream(gbk2Iso(remote));
		byte[] bytes = new byte[1024];
		long step = lRemoteSize / 100L;
		long process = 0L;
		long localSize = 0L;
		int c = 0;
		while ((c = in.read(bytes)) != -1) {
			os.write(bytes, 0, c);
			localSize += c;
			long nowProcess = localSize / step;
			if (nowProcess > process) {
				process = nowProcess;
				if (process % 10L == 0L) {
					LOGGER.info("下载进度：" + process);
				}
			}
		}
		in.close();
	}
	
	/* 
     * 从FTP服务器下载文件 
     *  
     * @param ftpHost FTP IP地址 
     *  
     * @param ftpUserName FTP 用户名 
     *  
     * @param ftpPassword FTP用户名密码 
     *  
     * @param ftpPort FTP端口 
     *  
     * @param ftpPath FTP服务器中文件所在路径 格式： ftptest/aa 
     *  
     * @param localPath 下载到本地的位置 格式：H:/download 
     *  
     * @param fileName 文件名称 
     */  
    public void downloadFtpFile(String ftpPath, String localPath, String fileName,String local_filename) {  
        try {  
        	//getBytes时
        	//若ftp服务器的编码为UTF-8，应该用UTF-8，比如测试时的236服务器
        	//若ftp服务器的编码为GBK，应该用GBK，比如测试时的175服务器，85服务器
        	
        	//开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
        	String LOCAL_CHARSET = "GBK";
//        	if(FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
//        		LOCAL_CHARSET = "UTF-8";
//        	}
        	ftpClient.setControlEncoding(LOCAL_CHARSET);// 中文支持  
        	
        	String SERVER_CHARSET = "ISO-8859-1";
        	
            String ftpPath2 = new String(ftpPath.getBytes(LOCAL_CHARSET),SERVER_CHARSET);
            this.ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
            this.ftpClient.enterLocalPassiveMode();  
            boolean flag1 = this.ftpClient.changeWorkingDirectory(ftpPath2); 
            if (!flag1) {
				LOGGER.error("没有找到" + ftpPath + "---该路径");
                return;
            }
            File localFile = new File(localPath + File.separatorChar + local_filename);  
            
//            File localFile = new File(localPath);  
            OutputStream os = new FileOutputStream(localFile);  
            //复制文件流
            boolean flag2 = ftpClient.retrieveFile(new String(fileName.getBytes(LOCAL_CHARSET),SERVER_CHARSET), os); 
//            boolean flag2 = ftpClient.retrieveFile(fileName, os); 
            if (!flag2) {
				LOGGER.error("没有找到" + fileName + "---该文件");
                localFile.delete();
            }
            os.close();  
            ftpClient.logout();
			LOGGER.info("成功下载远程文件"+ftpPath+""+fileName+"......");
        } catch (FileNotFoundException e) {
			LOGGER.error("没有找到" + ftpPath + "文件");
            e.printStackTrace();
        } catch (SocketException e) {
			LOGGER.error("连接FTP失败.");
            e.printStackTrace();
        } catch (IOException e) {  
            e.printStackTrace();
			LOGGER.error("文件读取错误。");
            e.printStackTrace();
        }  
    }

}
