package com.cloud.center.config.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/** 
 * @Description: TODO
 * @date 2019年7月9日 上午11:12:31 
 * @ClassName: CommandLineRunnerImpl 
 */
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("通过实现CommandLineRunner接口，在spring boot项目启动后打印参数");
        for (String arg : args) {
            System.out.print(arg + " ");
        }
        System.out.println();
    }
}
