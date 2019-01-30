package com.app.controller;

import com.app.service.impl.LoginServiceImpl;
import com.bobo.domain.AuthUser;
import com.mybatis.pojo.China;
import com.mybatis.pojo.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SystemController {

    @Autowired
    private LoginServiceImpl loginService;

    @PostMapping("/system/menus/{id}")
    public List<SysMenu> queryListMenu(@PathVariable(value = "id") String id){
        return loginService.queryListMenu(id);
    }
    /**
     * 获取全国所有的地区信息
     * @return
     */
    @GetMapping("/system/chinas")
    @ResponseBody
    List<China> queryAllChinas(){
        return loginService.queryAllChinas();
    }

    /**
     * 系统token 认证用户
     * @return
     */
    @PostMapping("/system/token/auth")
    @ResponseBody
    String queryUserToToken(@RequestBody AuthUser user){
        return loginService.queryUserToToken(user);
    }
}
