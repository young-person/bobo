package com.app.ftp.impl;

import com.app.ftp.FTPConfig;
import org.apache.commons.net.ftp.FTP;

public class FTPConfigFactory {
	
	/** FTP地址 */
    private String host;
    /** FTP端口 */
    private int port=21;
    /** 用户 */
    private String username;
    /** 密码 */
    private String password;
    /** 缓冲区大小33KB */
    private int bufferSize = 1024*33;
    /** 链接延迟 */
    private int clientTimeout = 5000;

    /** FTP环境的换行符 **/
    private String ftpLineSeparator  = "/";
    /** 与 ftp 环境 文件分隔符的反向反斜线 */
    private String obliqueLine = "\\".equals(ftpLineSeparator)?"/":"\\" ;
    private String workingDirectory = ftpLineSeparator;
    private boolean peculiarPlatform = false;
    private String description ;
    
    //编码。
    // 主动，被动
    //文件模式
    private String controlEncoding = "ISO-8859-1";
    //是否被动  
    private boolean passiveMode = true;
    //文件传输类型
    private int fileType = FTP.BINARY_FILE_TYPE;
    //private 
    //setPassiveMode
    public FTPConfig createFTPConfig(){
    	return new _DefaultFTPConfig(host, port, username, password, bufferSize, clientTimeout, ftpLineSeparator, obliqueLine, workingDirectory, peculiarPlatform, description, controlEncoding, passiveMode, fileType);
    }
    public void setControlEncoding(String controlEncoding) {
		this.controlEncoding = controlEncoding;
	}
    public String getControlEncoding() {
		return controlEncoding;
	}
    public void setPassiveMode(boolean passiveMode) {
		this.passiveMode = passiveMode;
	}
    public boolean isPassiveMode() {
		return passiveMode;
	}
    public void setFileType(int fileType) {
		this.fileType = fileType;
	}
    public int getFileType() {
		return fileType;
	}
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getWorkingDirectory() {
        return workingDirectory;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }
    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }
    public int getClientTimeout() {
        return clientTimeout;
    }

    public void setClientTimeout(int clientTimeout) {
        this.clientTimeout = clientTimeout;
    }
    public String getObliqueLine() {
        return obliqueLine;
    }
    public String getFtpLineSeparator() {
        return ftpLineSeparator;
    }

    public void setFtpLineSeparator(String ftpLineSeparator) {
        this.ftpLineSeparator = ftpLineSeparator;
        this.obliqueLine = "\\".equals(ftpLineSeparator)?"/":"\\" ;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPeculiarPlatform(boolean peculiarPlatform) {
        this.peculiarPlatform = peculiarPlatform;
    }

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
    
    private class _DefaultFTPConfig implements FTPConfig{

    	public _DefaultFTPConfig(String host, int port, String username, String password, int bufferSize, int clientTimeout,
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
}
