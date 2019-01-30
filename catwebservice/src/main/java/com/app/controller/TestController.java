package com.app.controller;

import com.app.feign.LoginService;
import com.app.pojo.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TestController {
    @Autowired
    private LoginService loginService;
    @GetMapping(value = "test")
    public ModelAndView list(ModelAndView mv) {
        mv.setViewName("/services");
        mv.addObject("title","欢迎使用Thymeleaf!");
        loginService.queryAllChinas();
        List<SysMenu> list = loginService.queryListMenu("1");
        return mv;
    }
}
