package com.app.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface DemoService {
    @WebMethod
    public String sayHello(@WebParam(name = "user") String user);
}
