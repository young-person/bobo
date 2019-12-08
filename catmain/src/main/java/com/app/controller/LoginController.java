package com.app.controller;



import com.alibaba.fastjson.JSONObject;
import com.app.service.impl.AuthManagerServiceImpl;
import com.app.service.impl.LoginServiceImpl;
import com.bobo.constant.Measure;
import com.bobo.domain.AuthUser;
import com.bobo.domain.Bean;
import com.bobo.domain.ResultMeta;
import com.bobo.domain.TreeUtils;
import com.bobo.token.TokenManagerLocal;
import com.bobo.utils.CookieUtils;
import com.bobo.utils.TokenManager;
import com.cloud.feign.catmain.LoginActionFeign;
import com.mybatis.pojo.China;
import com.mybatis.pojo.SysMenu;
import com.mybatis.pojo.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController implements LoginActionFeign {


    @Override
    @PostMapping("/system/token/auth")
    @ResponseBody
    public ResultMeta queryUserToToken(AuthUser user) {

        String tokenID = loginService.queryUserToToken(user);
        ResultMeta meta = new ResultMeta();

        if (StringUtils.isNotBlank(tokenID)){
            meta.success(tokenID);
        }else{
            meta.failure();
        }
        return meta;
    }

    @Override
    @RequestMapping(value = "/user/validate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> validate(@RequestBody AuthUser user){
        return loginService.validate(user);
    }

    @Override
    @RequestMapping(value = "/user/token", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<SysUser> loginToken(String token) {
        return loginService.loginToken(token);
    }

    @Override
    @GetMapping("/system/menus/{id}")
    public List<SysMenu> queryListMenu(String id) {
        return authManagerServiceImpl.queryListMenu(id);
    }

    @Override
    @GetMapping("/system/chinas")
    public List<China> queryAllChinas() {
        return loginService.queryAllChinas();
    }



    @Autowired
    private AuthManagerServiceImpl authManagerServiceImpl;

    @Autowired
    private LoginServiceImpl loginService;

    private TokenManager manager = new TokenManagerLocal();
    @RequestMapping(value = {"","/","index"})
    public ModelAndView toIndex(){
        ModelMap model = new ModelMap();
        return new ModelAndView("index", model);
    }
    @RequestMapping(value = "home")
    public ModelAndView doPageLogin(){
        ModelMap model = new ModelMap();
        List<SysMenu> menus = authManagerServiceImpl.queryListMenu(null);
        List<Bean>  beans = TreeUtils.buildListTree(menus);
        model.put("beans",beans);
        return new ModelAndView("home", model);
    }

    @RequestMapping(value = "/system/file")
    public ModelAndView toFile(){
        ModelMap model = new ModelMap();
        return new ModelAndView("file.ftl", model);
    }

    @RequestMapping(value = {"/page/{views}/{name}"})
    public ModelAndView toPage(@PathVariable(value="name")String name, @PathVariable(value="views")String views,
                               HttpServletRequest request,
                               HttpServletResponse response){
        ModelMap model = new ModelMap();
        List<SysMenu> menus = authManagerServiceImpl.queryListMenu("0");
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

    @PostMapping(value = "/system/login",produces="application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<ResultMeta> login(@RequestBody AuthUser user, HttpServletRequest request,
                                            HttpServletResponse response){
        String token = CookieUtils.getCookie(request, Measure.head_Authorization);
        if (StringUtils.isNotBlank(token)&& null != manager.validator(token)){
            //直接跳转

        }
        token = request.getHeader(Measure.head_Authorization);



        ResultMeta meta = new ResultMeta();
        if(StringUtils.isEmpty(user.getUsername())){
            meta.failure("用户名不能为空");
            return new ResponseEntity<ResultMeta>(meta, HttpStatus.OK);
        }
        if(StringUtils.isEmpty(user.getPassword())){
            meta.failure("密码不能为空");
            return new ResponseEntity<ResultMeta>(meta, HttpStatus.OK);
        }
//        List<SysUser> list = loginService.validateAuthUser(user);
//        if (list.size()==0){
//            meta.failure("用户或密码不匹配");
//            return new ResponseEntity<ResultMeta>(meta, HttpStatus.OK);
//        }
        //用户存在
        token = loginService.queryUserToToken(user);
        JSONObject object = new JSONObject();
        object.put(Measure.head_Authorization,token);
        object.put("user","111");
        meta.success(object);
        return new ResponseEntity<ResultMeta>(meta, HttpStatus.OK);
    }






}
