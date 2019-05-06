package com.app.cattask.pojo;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UserToken extends UsernamePasswordToken implements Cloneable{

    private String token;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserToken(String username,String password){
        super(username,password);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
