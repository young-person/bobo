package com.test.rpc;

import com.bobo.test.DemoService;

public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }

}
