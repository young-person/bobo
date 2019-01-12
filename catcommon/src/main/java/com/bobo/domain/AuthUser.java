package com.bobo.domain;

public class AuthUser {
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String userName;
    private String password;

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(userName).append("|").append(password).append("|").append(System.currentTimeMillis());
        return s.toString();
    }
    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof AuthUser) {
            AuthUser user = (AuthUser)obj;
            return user.getUserName().equals(this.getUserName()) && user.getPassword().equals(this.getPassword());
        }
        return false;
    }
    @Override
    public int hashCode() {
        return userName.hashCode() * password.hashCode();
    }
}
