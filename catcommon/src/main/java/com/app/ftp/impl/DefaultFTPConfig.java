package com.app.ftp.impl;


import com.app.ftp.FTPConfig;

public class DefaultFTPConfig implements FTPConfig {

	public DefaultFTPConfig(String host, int port, String username, String password, int bufferSize, int clientTimeout,
			String ftpLineSeparator, String obliqueLine, String workingDirectory, boolean peculiarPlatform,
			String description, String controlEncoding, boolean passiveMode, int fileType) {
		super();
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.bufferSize = bufferSize;
		this.clientTimeout = clientTimeout;
		this.ftpLineSeparator = ftpLineSeparator;
		this.obliqueLine = obliqueLine;
		this.workingDirectory = workingDirectory;
		this.peculiarPlatform = peculiarPlatform;
		this.description = description;
		this.controlEncoding = controlEncoding;
		this.passiveMode = passiveMode;
		this.fileType = fileType;
	}


	/** FTP地址 */
    private final String host;
    /** FTP端口 */
    private final int port;
    /** 用户 */
    private final String username;
    /** 密码 */
    private final String password;
    /** 缓冲区大小33KB */
    private final int bufferSize;
    /** 链接延迟 */
    private final int clientTimeout;

    /** FTP环境的换行符 **/
    private final String ftpLineSeparator ;
    /** 与 ftp 环境 文件分隔符的反向反斜线 */
    private final String obliqueLine ;
    private final String workingDirectory ;
    private final boolean peculiarPlatform ;
    private final String description ;
    
    
    private final String controlEncoding ;
    //是否被动  
    private final boolean passiveMode;
    //文件传输类型
    private int fileType ;
    
    public String getHost() {
        return host;
    }

  
    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getUsername() {
        return username;
    }

   
    @Override
    public String getPassword() {
        return password;
    }

    
    @Override
    public String getWorkingDirectory() {
        return workingDirectory;
    }

   
    @Override
    public int getBufferSize() {
        return bufferSize;
    }

   
    @Override
    public int getClientTimeout() {
        return clientTimeout;
    }

   
    @Override
    public String getObliqueLine() {
        return obliqueLine;
    }
    @Override
    public String getFtpLineSeparator() {
        return ftpLineSeparator;
    }

   
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isPeculiarPlatform() {
        return peculiarPlatform;
    }

    @Override
    public String toString() {
        return "FTPConfig{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", bufferSize=" + bufferSize +
                ", clientTimeout=" + clientTimeout +
                ", ftpLineSeparator='" + ftpLineSeparator + '\'' +
                ", obliqueLine='" + obliqueLine + '\'' +
                ", workingDirectory='" + workingDirectory + '\'' +
                ", peculiarPlatform=" + peculiarPlatform +
                ", description='" + description + '\'' +
                '}';
    }


	@Override
	public boolean isPassiveMode() {
		return passiveMode;
	}


	@Override
	public String getControlEncoding() {
		return controlEncoding;
	}


	@Override
	public int getFileType() {
		return fileType;
	}
}
