package com.bobo.domain;

public class AuthUser {
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

    private String username;
    private String password;

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(username).append("|").append(password).append("|").append(System.currentTimeMillis());
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
            return user.getUsername().equals(this.getUsername()) && user.getPassword().equals(this.getPassword());
        }
        return false;
    }
    @Override
    public int hashCode() {
        return username.hashCode() * password.hashCode();
    }
}
