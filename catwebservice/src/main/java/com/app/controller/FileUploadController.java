package com.app.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemHeaders;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class FileUploadController {
    private static String tmp = "D://temp//";

    protected static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    @PostMapping(value="/remote/upload/bck")
    public @ResponseBody ResponseEntity<String> uploadImg(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
        if (file.isEmpty()) {
            return new ResponseEntity<String>("上传文件不能为空", HttpStatus.OK);
        }
        String saveFileName = file.getOriginalFilename();
        File saveFile = new File(tmp + saveFileName);
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(saveFile));
            out.write(file.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("上传文件失败",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("您已成功上传文件：" + file.getOriginalFilename(),HttpStatus.OK);
    }
    @PostMapping(value = "/remote/upload")
    @ResponseBody
    public ResponseEntity<String> singleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<String>("上传文件不能为空", HttpStatus.OK);
        }
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(tmp + file.getOriginalFilename());
            Files.write(path, bytes);
            logger.info("文件上传成功:"+file.getName()+"文件大小:"+file.getSize());
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("文件上传失败:"+file.getName()+"文件大小:"+file.getSize(),e);
            return new ResponseEntity<String>("上传文件失败",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("您已成功上传文件：" + file.getOriginalFilename(),HttpStatus.OK);
    }


    @RequestMapping(value = "/remote/upload/speed",method= RequestMethod.POST)
    @ResponseBody
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        if(ServletFileUpload.isMultipartContent(request)){
            logger.info("/remote/upload/speed、接口有上传文件");
        }
        String groupname = request.getParameter("groupname");
        String c = request.getParameter("file");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload fileUpload = new ServletFileUpload(factory);
        fileUpload.setHeaderEncoding("UTF-8");
        fileUpload.setFileSizeMax(100*1024*1024);
        fileUpload.setSizeMax(100*1024*1024);
        try {
            List<FileItem> list = fileUpload.parseRequest(new ServletRequestContext(request));
            FileItemIterator fileItemIterator = fileUpload.getItemIterator(request);
            while (fileItemIterator.hasNext()){
                FileItemStream stream = fileItemIterator.next();
                InputStream inputStream = stream.openStream();
                String fieldName = stream.getFieldName();
                String name = stream.getName();
                FileItemHeaders headers = stream.getHeaders();
            }
            for (FileItem item : list){
                if (item.isFormField()) {
                    String filedName = item.getFieldName();
                    String filedValue = item.getString("UTF-8");
                }else{
                    String fileName = item.getName();
                    if(StringUtils.isBlank(fileName)){
                        continue;
                    }
                    fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
                    String filePath = tmp + fileName;
                    InputStream in = item.getInputStream();
                    OutputStream out = new FileOutputStream(filePath);
                    byte b[] = new byte[1024];
                    int len = -1;
                    while((len=in.read(b))!=-1){
                        out.write(b, 0, len);
                    }
                    out.close();
                    in.close();
                    item.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
