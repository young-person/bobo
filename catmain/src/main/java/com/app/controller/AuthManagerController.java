package com.app.controller;

import com.app.service.impl.AuthManagerServiceImpl;
import com.bobo.domain.Bean;
import com.bobo.domain.TreeUtils;
import com.mybatis.pojo.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 授权 认证管理
 */

@Controller
public class AuthManagerController {

    @Autowired
    private AuthManagerServiceImpl authManagerServiceImpl;

    @RequestMapping(value = {"/page/authorization/{name}"})
    public ModelAndView toPage(@PathVariable(value="name")String name){
        ModelMap model = new ModelMap();
        List<SysMenu> menus = authManagerServiceImpl.queryListMenu(null);
        List<Bean>  beans = TreeUtils.buildListTree(menus);
        model.put("beans",beans);
        StringBuilder builder = new StringBuilder();
        builder.append("authorization/");
        builder.append(name);
        model.put("menus",menus);
        return new ModelAndView(builder.toString(), model);
    }

    @RequestMapping(value = {"/page/authorization/{name}/{delete}"})
    public ModelAndView deleteMenutoPage(@PathVariable(value="name")String name,
                                         @PathVariable(value="delete")Integer delete){

        int count = authManagerServiceImpl.deleteMenu(delete);
//
//        List<SysMenu> menus = authManagerServiceImpl.queryListMenu(null);
//        List<Bean>  beans = TreeUtils.buildListTree(menus);
//        ModelMap model = new ModelMap();
//        model.put("beans",beans);
//        StringBuilder builder = new StringBuilder();
//        builder.append("authorization/");
//        builder.append(name);
//        model.put("menus",menus);

        return new ModelAndView("redirect:/page/authorization/sysmenu");
    }


}
