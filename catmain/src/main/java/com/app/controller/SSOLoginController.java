package com.app.controller;

import com.bobo.constant.Measure;
import com.bobo.token.TokenManagerLocal;
import com.bobo.utils.CookieUtils;
import com.bobo.utils.TokenManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SSOLoginController {



    @GetMapping(value = "/login",produces="application/json;charset=UTF-8")
    public String login(HttpServletRequest request, @PathVariable String backurl,@PathVariable String appcode,
                        HttpServletResponse response){
        String token = CookieUtils.getCookie(request, Measure.head_Authorization);
        TokenManager manager = new TokenManagerLocal();
        if (StringUtils.isNotBlank(token) && manager.validator(token) !=null){
            //直接跳转
            return "redirect:"+authBackUrl(backurl, token);
        }
        token = request.getHeader(Measure.head_Authorization);
        if (StringUtils.isNotBlank(token) && manager.validator(token) !=null){
            //直接跳转
            return "redirect:"+authBackUrl(backurl, token);
        }
        return "redirect:index";
    }

    private String authBackUrl(String backUrl, String token) {
        StringBuilder sbf = new StringBuilder(backUrl);
        if (backUrl.indexOf("?") > 0) {
            sbf.append("&");
        }
        else {
            sbf.append("?");
        }
        sbf.append(Measure.head_Authorization).append("=").append(token);
        return sbf.toString();
    }
}
