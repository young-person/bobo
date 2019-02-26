package com.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "cat.cfg")
@Component
public class MProperties {

    private String alipay_gateway;
    private String alipay_appid;
    private String alipay_rsaprivatekey;
    private String alipay_format;
    private String alipay_charset;
    private String alipay_public_key;
    private String alipay_sign_type;
    private String alipay_rsapublickey;

    private String returnUrl;
    private String notifyUrl;

    public String getAlipay_gateway() {
        return alipay_gateway;
    }

    public void setAlipay_gateway(String alipay_gateway) {
        this.alipay_gateway = alipay_gateway;
    }

    public String getAlipay_appid() {
        return alipay_appid;
    }

    public void setAlipay_appid(String alipay_appid) {
        this.alipay_appid = alipay_appid;
    }

    public String getAlipay_rsaprivatekey() {
        return alipay_rsaprivatekey;
    }

    public void setAlipay_rsaprivatekey(String alipay_rsaprivatekey) {
        this.alipay_rsaprivatekey = alipay_rsaprivatekey;
    }

    public String getAlipay_format() {
        return alipay_format;
    }

    public void setAlipay_format(String alipay_format) {
        this.alipay_format = alipay_format;
    }

    public String getAlipay_charset() {
        return alipay_charset;
    }

    public void setAlipay_charset(String alipay_charset) {
        this.alipay_charset = alipay_charset;
    }

    public String getAlipay_public_key() {
        return alipay_public_key;
    }

    public void setAlipay_public_key(String alipay_public_key) {
        this.alipay_public_key = alipay_public_key;
    }

    public String getAlipay_sign_type() {
        return alipay_sign_type;
    }

    public void setAlipay_sign_type(String alipay_sign_type) {
        this.alipay_sign_type = alipay_sign_type;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getAlipay_rsapublickey() {
        return alipay_rsapublickey;
    }

    public void setAlipay_rsapublickey(String alipay_rsapublickey) {
        this.alipay_rsapublickey = alipay_rsapublickey;
    }
}
