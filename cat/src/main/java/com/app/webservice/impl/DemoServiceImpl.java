package com.app.webservice.impl;

import com.app.webservice.DemoService;

import java.util.Date;

public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String user) {
        return user+":hello"+"("+new Date()+")";
    }
}

