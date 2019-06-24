package com.core.generator;

import com.core.registrycenter.CoordinatorRegistryCenter;

public class RegisterDto {
    private String app;

    private String host;

    private CoordinatorRegistryCenter coordinatorRegistryCenter;

    public String getApp() {
        return app;
    }

    public RegisterDto(String app, String host, CoordinatorRegistryCenter coordinatorRegistryCenter){
        this.app = app;
        this.host = host;
        this.coordinatorRegistryCenter = coordinatorRegistryCenter;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public CoordinatorRegistryCenter getCoordinatorRegistryCenter() {
        return coordinatorRegistryCenter;
    }

    public void setCoordinatorRegistryCenter(CoordinatorRegistryCenter coordinatorRegistryCenter) {
        this.coordinatorRegistryCenter = coordinatorRegistryCenter;
    }
}
