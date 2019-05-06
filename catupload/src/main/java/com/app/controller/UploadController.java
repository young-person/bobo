package com.app.controller;

import com.app.common.BaseController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件上传
 */
@Controller
public class UploadController extends BaseController {
    @GetMapping(value = "services")
    public ModelAndView list(ModelAndView mv) {
        mv.setViewName("/services");
        mv.addObject("title","欢迎使用Thymeleaf!");
        return mv;
    }
    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping(value = "/upload",produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public ResponseEntity<String> singleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<String>("上传文件不能为空",HttpStatus.CONTINUE);
        }
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            logger.info("文件上传成功:"+file.getName()+"文件大小:"+file.getSize());
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("文件上传失败:"+file.getName()+"文件大小:"+file.getSize(),e);
            return new ResponseEntity<String>("<p style='background: red;'>上传文件失败</p>",HttpStatus.CONTINUE);

        }
        return new ResponseEntity<String>("您已成功上传文件：<p style='background: blue;'>\" + file.getOriginalFilename() + \"</p>",HttpStatus.CONTINUE);
    }

}
