package com.config;

import com.app.webservice.DemoService;
import com.app.webservice.impl.DemoServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServiceConfig {
//    @Bean
//    public ServletRegistrationBean dispatcherServlet() {
//        return new ServletRegistrationBean(new CXFServlet(),"/webservice/*");
//    }
//
//    @Bean(name = Bus.DEFAULT_BUS_ID)
//    public SpringBus springBus() {
//        return new SpringBus();
//    }
//
//    @Bean
//    public DemoService getDemoService(){
//        return new DemoServiceImpl();
//    }
//    @Bean
//    public Endpoint endpoint() {
//        EndpointImpl endpoint = new EndpointImpl(springBus(), getDemoService());
//        endpoint.publish("/api");
//        endpoint.getInInterceptors().add(new LoggingInInterceptor());
//        endpoint.getInInterceptors().add(new LoggingOutInterceptor());
//        return endpoint;
//    }
}
