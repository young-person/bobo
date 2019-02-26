package com.app.properties;

import java.util.Timer;

public final class PropertiesForFile {

    private static final String source_src = "";
    private volatile static PropertiesForFile _instance = null;

    public static PropertiesForFile getInstance(){
        if (_instance != null){
            return _instance;
        }else{
            synchronized (PropertiesForFile.class){
                if (_instance == null){
                    return new PropertiesForFile();
                }
            }
        }
        return _instance;
    }

    private final static Timer timer = new Timer();

    private String alipay_public_key = "alipay.alipay_public_key";
    private String charset = "alipay.charset";
    private String sign_type = "alipay.sign_type";

    public String getAlipay_public_key() {
        return alipay_public_key;
    }

    public void setAlipay_public_key(String alipay_public_key) {
        this.alipay_public_key = alipay_public_key;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }
}
