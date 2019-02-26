package com.app.ftp;

public interface FTPConfig {
  
    public String getHost();

    public int getPort() ;

    public String getUsername();
  
    public String getPassword() ;
  
    public String getWorkingDirectory() ;

    public int getBufferSize() ;

    public int getClientTimeout() ;

    public String getObliqueLine() ;

    public String getFtpLineSeparator() ;

    public String getDescription();

    public boolean isPeculiarPlatform() ;

    public boolean isPassiveMode() ;
    
    public String getControlEncoding();
    
    public int getFileType();
}
