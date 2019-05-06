package com.app.controller;

import com.bobo.domain.Bean;
import com.bobo.domain.TreeUtils;
import com.cloud.feign.catmain.LoginActionFeign;
import com.mybatis.pojo.SysMenu;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private LoginActionFeign loginService;

    @RequestMapping(value = {"/upload/{views}/{name}"})
    public ModelAndView toPage(@PathVariable(value="name")String name, @PathVariable(value="views")String views){
        List<SysMenu> menus = loginService.queryListMenu("0");

        ModelMap model = new ModelMap();

        List<Bean>  beans = TreeUtils.buildListTree(menus);
        model.put("beans",beans);

        StringBuilder builder = new StringBuilder();
        if (StringUtils.isNotBlank(views)){
            builder.append(views);
            builder.append("/");
            builder.append(name);
        }else{
            builder.append(name);
        }
        return new ModelAndView(builder.toString(), model);
    }
}
