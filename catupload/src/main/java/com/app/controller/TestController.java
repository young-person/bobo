package com.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import com.app.common.BaseController;
import com.app.pojo.FileInfo;
import com.app.service.impl.AliyunOssService;
import com.bobo.constant.CProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

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
    @Autowired
    private AliyunOssService aliyunOssService;

    @Autowired
    private OSSClient aliyunOssClient;

    @GetMapping("/aliyun/upload1")
    public String upload1() {
        PutObjectResult putObjectResult = aliyunOssClient.putObject(CProperties.ALIYUN_OSS_BUCKET_NAME, "text.txt",
                new ByteArrayInputStream("Hello OSS".getBytes()));
        return "success";
    }

    @GetMapping("/aliyun/upload2")
    public String upload2() throws FileNotFoundException {
        File file = new File("d:\\zheng.png");
        PutObjectResult putObjectResult = aliyunOssClient.putObject(CProperties.ALIYUN_OSS_BUCKET_NAME, "file.png", file);
        return "success";
    }

    @GetMapping("/aliyun/download1")
    public String download1() throws IOException {
        StringBuffer result = new StringBuffer();
        OSSObject ossObject = aliyunOssClient.getObject(CProperties.ALIYUN_OSS_BUCKET_NAME, "text.txt");
        InputStream content = ossObject.getObjectContent();
        if (content != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                result.append("\n" + line);
            }
            content.close();
        }
        return result.toString();
    }

    @GetMapping("/aliyun/download2")
    public String download2() throws IOException {
        return "http://" + CProperties.ALIYUN_OSS_BUCKET_NAME + "." + CProperties.ALIYUN_OSS_ENDPOINT + "/file.png";
    }

    @GetMapping("/aliyun/upload")
    public String upload(Model model) {
        JSONObject policy = aliyunOssService.policy();
        model.addAttribute("policy", policy);
        return "/aliyun/upload";
    }
    
    @RequestMapping(value =  "attendee",method=RequestMethod.POST)
    @ResponseBody
    public String name(@RequestParam("requiredKey") String requiredKey,@RequestParam("file") MultipartFile file) {
		System.out.println(requiredKey);
		System.out.println(file);
		return "aaaa"+requiredKey;
	}
}
