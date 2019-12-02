package com.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class RedisLockTest {
    @Test
    public void test1() {


    }

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }

    private static String key = "lock-test";
    private static int count = 10;


    public void run() {
        IntStream.range(0, 100).forEach(i -> {
            Thread run = new Thread(new Runnable() {
                @Override
                public void run() {

                }
            });
            run.start();
        });
    }

}
