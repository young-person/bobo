package com.app.ftp;
/**
 * Created by 寿继伟 on 2018/11/10.
 */
public class FTPConfig {
    /** FTP地址 */
    private String host;
    /** FTP端口 */
    private int port;
    /** 用户 */
    private String username;
    /** 密码 */
    private String password;
    /** 缓冲区大小1M */
    private int bufferSize = 1024*1024;
    /** 链接延迟 */
    private int clientTimeout = 5000;

    /** FTP环境的换行符 **/
    private String ftpLineSeparator  = "/";
    /** 与 ftp 环境 文件分隔符的反向反斜线 */
    private String obliqueLine = "\\".equals(ftpLineSeparator)?"/":"\\" ;
    private String workingDirectory = ftpLineSeparator;
    private String description ;
    public FTPConfig() {}
    public FTPConfig(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
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
                ", description='" + description + '\'' +
                '}';
    }
}
