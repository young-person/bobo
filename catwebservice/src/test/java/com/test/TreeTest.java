package com.test;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TreeTest {
    @Test
    public void test1() {


        System.out.println(1);
    }

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }


    @HystrixCommand(fallbackMethod = "errorMethod")
    public void test2() {

    }

    public void errorMethod(){
        
    }

}



