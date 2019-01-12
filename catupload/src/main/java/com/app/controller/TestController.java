package com.app.controller;

import com.app.common.BaseController;
import com.app.pojo.FileInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController extends BaseController {
    @GetMapping(value = "test")
    public String getDemo(){
        FileInfo fileInfo = new FileInfo();
        fileInfo.setPath("D:\\mypicture\\IMG_0128.JPG");
        fileInfo.setFileName("apache");
        doRemotePutFile(fileInfo);
        return "9001";
    }
//
//    @GetMapping(value = "xxx")
//    public ModelAndView list(Model model) {
//        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
//        Map<String,String> m = new HashMap<String,String>();
//        m.put("id","1");
//        m.put("name","name");
//        m.put("age","10");
//        list.add(m);
//        model.addAttribute("userList", list);
//        model.addAttribute("title", "用户管理");
//        return new ModelAndView("footer", "userModel", model);
//    }
}
