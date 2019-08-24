package com.test;

import com.app.utils.RedisLockUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    private static String key = "lock-test";
    private static int count = 10;

    public void test() {
        RedisLockUtil lock = new RedisLockUtil(redisTemplate, key, 10000, 20000);
        try {
            if (lock.lock()) {
                Thread.sleep(1000);
                if (count >= 1) {

                    count--;

                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，因为可能客户端因为某个耗时的操作而挂起，
            // 操作完的时候锁因为超时已经被别人获得，这时就不必解锁了.
            if (!lock.isExpired()) {
                lock.unlock();
            }

        }
    }

    public void run() {
        IntStream.range(0, 100).forEach(i -> {
            Thread run = new Thread(new Runnable() {
                @Override
                public void run() {
                    test();
                }
            });
            run.start();
        });
    }

}
