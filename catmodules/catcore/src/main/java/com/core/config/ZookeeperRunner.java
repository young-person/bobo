package com.core.config;

import com.core.properties.CatCloudProperties;
import com.core.zookeeper.RegistryCenterFactory;
import org.springframework.boot.CommandLineRunner;

import java.net.InetAddress;

public class ZookeeperRunner implements CommandLineRunner {
    private CatCloudProperties cloudProperties;
    private String applicationName;
    @Override
    public void run(String... strings) throws Exception {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        RegistryCenterFactory.startup(cloudProperties, hostAddress, applicationName);
    }
}
