package com.bobo.domain;

public class FtpConfig {
    private String address;
    private int port;
    private String username;
    private String password;
    private String root;
    private String ftpFileSeparator;
    private String ftpObliqueLine;
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getFtpFileSeparator() {
        return ftpFileSeparator;
    }

    public void setFtpFileSeparator(String ftpFileSeparator) {
        this.ftpFileSeparator = ftpFileSeparator;
    }

    public String getFtpObliqueLine() {
        return ftpObliqueLine;
    }

    public void setFtpObliqueLine(String ftpObliqueLine) {
        this.ftpObliqueLine = ftpObliqueLine;
    }
}
