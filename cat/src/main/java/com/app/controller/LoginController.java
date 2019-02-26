package com.app.controller;



import com.app.service.impl.LoginServiceImpl;
import com.bobo.constant.Measure;
import com.bobo.domain.AuthUser;
import com.bobo.domain.Bean;
import com.bobo.domain.ResultMeta;
import com.bobo.domain.TreeUtils;
import com.mybatis.pojo.SysMenu;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
    private String appname = "springcloud";

    @Autowired
    private LoginServiceImpl loginService;
    @RequestMapping("/page/{name}")
    public ModelAndView toPage(@PathVariable(value="name")String name){
        ModelMap model = new ModelMap();
        return new ModelAndView(name, model);
    }

    @RequestMapping(value = {"","/","index"})
    public ModelAndView toIndex(){
        ModelMap model = new ModelMap();
        return new ModelAndView("index", model);
    }
    @RequestMapping(value = "home")
    public ModelAndView doPageLogin(){
        ModelMap model = new ModelMap();
        List<SysMenu> menus = loginService.queryListMenu(null);
        SysMenu sysMenu =  new SysMenu();
        List<Bean>  beans = TreeUtils.buildListTree(menus);
        model.put("beans",beans);
        model.put("appname",appname);
        return new ModelAndView("home", model);
    }

    @RequestMapping(value = "/system/file")
    public ModelAndView toFile(){
        ModelMap model = new ModelMap();
        return new ModelAndView("file.ftl", model);
    }

    @PostMapping(value = "/system/login",produces="application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<ResultMeta> login(@RequestBody AuthUser user,HttpServletResponse response){
        ResultMeta meta = new ResultMeta();
        Map<String,Object> map = new HashMap<String,Object>();
        if(StringUtils.isEmpty(user.getUsername())){
            meta.failure("用户名不能为空");
            return new ResponseEntity<ResultMeta>(meta, HttpStatus.OK);
        }
        if(StringUtils.isEmpty(user.getPassword())){
            meta.failure("密码不能为空");
            return new ResponseEntity<ResultMeta>(meta, HttpStatus.OK);
        }
        String token = loginService.queryUserToToken(user);
        if (StringUtils.isBlank(token)){
            meta.failure("用户和密码不匹配");
            return new ResponseEntity<ResultMeta>(meta, HttpStatus.OK);
        }
        meta.success("登录成功");
        response.setHeader(Measure.head_Authorization,token);
        return new ResponseEntity<ResultMeta>(meta, HttpStatus.OK);
    }


}
