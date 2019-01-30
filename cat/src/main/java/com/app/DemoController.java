package com.app;

import com.alibaba.fastjson.JSONObject;
import com.app.service.impl.LoginServiceImpl;
import com.bobo.base.BaseClass;
import com.bobo.domain.Bean;
import com.bobo.domain.TreeUtils;
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
    private LoginServiceImpl loginService;
    @GetMapping(value = "chinas/xml",produces = MediaType.APPLICATION_XML_VALUE)
    public List<China> queryChinas_xml(){
        List<China> list = loginService.queryAllChinas();
        TreeUtils.buildListTree(list);
        return loginService.queryAllChinas();
    }

    @GetMapping(value = "chinas/json",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String queryChinas_json(){
        List<China> list = loginService.queryAllChinas();
        List<Bean> beans = TreeUtils.buildListTree(list);
        return JSONObject.toJSONString(beans);
    }

}
