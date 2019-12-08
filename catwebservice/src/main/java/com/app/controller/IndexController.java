package com.app.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.app.config.CatWebServiceProperty;
import com.app.config.CatXml;
import com.app.pojo.SysMenu;
import com.bobo.constant.Measure;
import com.bobo.domain.AuthUser;
import com.bobo.domain.ResultMeta;
import com.bobo.domain.TreeUtils;
import com.bobo.utils.AESUtil;
import com.bobo.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class IndexController {

    @Autowired
    private CatWebServiceProperty serviceProperty;

    @RequestMapping(value = {"","/","index"})
    public ModelAndView toIndex(){
        ModelMap model = new ModelMap();
        return new ModelAndView("index", model);
    }

    @PostMapping(value = "/system/login",produces="application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<ResultMeta> login(@RequestBody AuthUser user, HttpServletRequest request,
                                            HttpServletResponse response){
        ResultMeta meta = new ResultMeta();

        String token = CookieUtils.getCookie(request, Measure.head_Authorization);
        if (this.checkToken(token)){
            return this.isOk(token,meta,response,request);
        }

        token = request.getHeader(Measure.head_Authorization);
        if (this.checkToken(token)){
            return this.isOk(token,meta,response,request);
        }

        if(StringUtils.isEmpty(user.getUsername())){
            meta.failure("用户名不能为空");
            return new ResponseEntity<ResultMeta>(meta, HttpStatus.OK);
        }
        if(StringUtils.isEmpty(user.getPassword())){
            meta.failure("密码不能为空");
            return new ResponseEntity<ResultMeta>(meta, HttpStatus.OK);
        }

        if (serviceProperty.getUserName().equals(user.getUsername()) && serviceProperty.getPassword().equals(user.getPassword())){
            token = new StringBuilder().append(AESUtil.aesEncode(user.getUsername())).append("__________").append(AESUtil.aesEncode(user.getPassword())).toString();
            JSONObject object = new JSONObject();
            object.put(Measure.head_Authorization,token);
            object.put("user",user.getUsername());
            meta.success(object);
            response.setHeader(Measure.head_Authorization,token);
            Cookie cookie = new Cookie(Measure.head_Authorization,token);
            cookie.setMaxAge(365 * 24 * 60 * 60);
            cookie.setPath("/");
            Cookie cookie2 = new Cookie(Measure.CAT_head_Authorization,token);
            cookie2.setMaxAge(365 * 24 * 60 * 60);
            cookie2.setPath("/");
            response.addCookie(cookie);
            response.addCookie(cookie2);
            request.getSession().setAttribute(Measure.head_Authorization,token);
            return new ResponseEntity<ResultMeta>(meta, HttpStatus.OK);
        }else{
            meta.failure("账号密码不正确");
            return new ResponseEntity<ResultMeta>(meta, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "home")
    public ModelAndView goHome(HttpServletRequest request) throws IOException {
        String token = CookieUtils.getCookie(request, Measure.head_Authorization);
        ModelMap model = new ModelMap();
        CatXml catXml = new CatXml();
        File file = ResourceUtils.getFile(catXml.getDataRoot());
        File menu = new File(file,"menu.json");
        if(!menu.exists()){
            menu.createNewFile();
        }
        JSONArray array = JSON.parseObject(new FileInputStream(menu), JSONArray.class);
        List<SysMenu> menus = JSONObject.parseArray(array.toJSONString(), SysMenu.class);
        if (Objects.isNull(menus)){
            menus = new ArrayList<>();
        }
        List<com.bobo.domain.Bean> beans = TreeUtils.buildListTree(menus);
        model.put("beans",beans);
        return new ModelAndView("home", model);
    }


    private boolean checkToken(String token){
        boolean sure = false;
        if (StringUtils.isNotBlank(token)){
            String[] s = token.split("__________");
            String u = AESUtil.aesDecode(s[0]);
            String p = AESUtil.aesDecode(s[1]);
            if (serviceProperty.getUserName().equals(u) && serviceProperty.getPassword().equals(p)){
                sure = true;
            }
        }
        return sure;
    }
    private ResponseEntity<ResultMeta> isOk(String token,ResultMeta meta,HttpServletResponse response,HttpServletRequest request){
        String[] s = token.split("__________");
        String u = AESUtil.aesDecode(s[0]);

        JSONObject object = new JSONObject();
        object.put(Measure.head_Authorization,token);
        object.put("user",u);
        meta.success(object);
        response.setHeader(Measure.head_Authorization,token);
        Cookie cookie = new Cookie(Measure.head_Authorization,token);
        cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);
        request.getSession().setAttribute(Measure.head_Authorization,token);
        return new ResponseEntity<>(meta, HttpStatus.OK);
    }


}
