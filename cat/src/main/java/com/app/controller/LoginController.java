package com.app.controller;


import com.app.service.LoginService;
import com.mybatis.pojo.SysMenu;
import com.mybatis.pojo.SysUser;
import com.bobo.domain.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;
    @RequestMapping(value = {"","/","index"})
    public ModelAndView toIndex(){
        ModelMap model = new ModelMap();
        return new ModelAndView("index", model);
    }
    @RequestMapping(value = "file")
    public ModelAndView toFile(){
        ModelMap model = new ModelMap();
        return new ModelAndView("file", model);
    }
    @RequestMapping(value = "home")
    public ModelAndView doJspLogin(){
        ModelMap model = new ModelMap();
        List<SysMenu> menus = loginService.queryListMenu();
        model.put("menus",menus);
        return new ModelAndView("home", model);
    }
    @GetMapping(value = "user")
    @ResponseBody
    public ResponseEntity querySelectOneUser(){
        SysUser sysUser =loginService.querySelectOneUser();
        return ResponseEntity.ok(sysUser);
    }
    @GetMapping(value = "menu")
    @ResponseBody
    public List<SysMenu> queryMenuSize(){
        return loginService.queryListMenu();
    }


    @PostMapping(value = "login")
    public ResponseEntity login(String username, String password){

        Map<String,Object> map = new HashMap<String,Object>();
        if(StringUtils.isEmpty(username)){
            map.put("msg","用户名不能为空");
            return ResponseEntity.ok().body(map);
        }
        if(StringUtils.isEmpty(password)){
            map.put("msg","密码不能为空");
            return ResponseEntity.ok().body(map);
        }
        map.put("ticket","");
        return ResponseEntity.ok().body(map);
    }

    @PostMapping(value = "login/token")
    public ResponseEntity<String> loginToken(@RequestBody AuthUser user){
        final String token = loginService.queryUserToToken(user);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

}
