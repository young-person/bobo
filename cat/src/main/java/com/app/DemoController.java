package com.app;

import com.alibaba.fastjson.JSONObject;
import com.app.service.LoginService;
import com.bobo.base.BaseClass;
import com.bobo.domain.Bean;
import com.mybatis.pojo.China;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoController extends BaseClass {
    @GetMapping(value = "test")
    public String getVal(){
        return "1";
    }
    @Autowired
    private LoginService loginService;
    @GetMapping(value = "chinas/xml",produces = MediaType.APPLICATION_XML_VALUE)
    public List<China> queryChinas_xml(){
        List<China> list = loginService.queryAllChinas();
        China china = new China();
        china.buildListTree(list);
        return loginService.queryAllChinas();
    }

    @GetMapping(value = "chinas/json",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String queryChinas_json(){
        List<China> list = loginService.queryAllChinas();
        China china = new China();
        List<Bean> beans = china.buildListTree(list);
        JSONObject object = new JSONObject();
        object.put("list",beans);
        System.out.println(object.toJSONString());
        return object.toJSONString();
    }
}
