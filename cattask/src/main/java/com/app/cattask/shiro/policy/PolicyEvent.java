package com.app.cattask.shiro.policy;

import com.bobo.enums.Message;

public abstract class PolicyEvent<T> {

    public abstract void init(T token);
    public static void main(String[] args) {
        Message.HAS_ONLINE.toString();
        System.out.printf(Message.HAS_ONLINE.getJson());
    }
}
