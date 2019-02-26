package com.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.app.service.impl.AliyunOssService;
import com.bobo.base.BaseClass;
import com.bobo.constant.CProperties;
import com.bobo.domain.ResultMeta;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/aliyun/oss")
public class OssController extends BaseClass {

    @Autowired
    private AliyunOssService aliyunOssService;

    /**
     * 签名生成
     * @param callback 跨域请求
     * @return
     */
    @GetMapping("/policy")
    @ResponseBody
    //@CrossOrigin(origins = "*", methods = RequestMethod.GET) // 该注解不支持JDK1.7
    public Object policy(@RequestParam(required = false) String callback) {
        JSONObject result = aliyunOssService.policy();
        if (StringUtils.isBlank(callback)) {
            return result;
        }
        MappingJacksonValue jsonp = new MappingJacksonValue(result);
        jsonp.setJsonpFunction(callback);
        return jsonp;
    }

    /**
     * 上传成功回调方法
     * @param request
     * @return
     */
    @PostMapping("callback")
    @ResponseBody
    public Object callback(HttpServletRequest request) {
        JSONObject data = new JSONObject();
        String filename = request.getParameter("filename");
        filename =
                "http://".concat(CProperties.ALIYUN_OSS_BUCKET_NAME).concat(".").concat(CProperties.ALIYUN_OSS_ENDPOINT).concat(
                        "/").concat(filename);
        data.put("filename", filename);
        data.put("size", request.getParameter("size"));
        data.put("mimeType", request.getParameter("mimeType"));
        data.put("width", request.getParameter("width"));
        data.put("height", request.getParameter("height"));
        ResultMeta meta = new ResultMeta();
        return meta.success(data);
    }

}
