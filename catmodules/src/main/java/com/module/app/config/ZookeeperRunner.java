package com.module.app.config;

import com.module.app.properties.CloudProperties;
import com.module.app.zookeeper.RegistryCenterFactory;
import org.springframework.boot.CommandLineRunner;

import java.net.InetAddress;

public class ZookeeperRunner implements CommandLineRunner {
    private CloudProperties cloudProperties;
    private String applicationName;
    @Override
    public void run(String... strings) throws Exception {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        RegistryCenterFactory.startup(cloudProperties, hostAddress, applicationName);
    }
}
