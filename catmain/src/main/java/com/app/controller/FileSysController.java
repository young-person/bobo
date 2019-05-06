package com.app.controller;

import com.app.service.impl.FileSysServiceImpl;
import com.bobo.base.BaseClass;
import com.bobo.constant.Measure;
import com.bobo.domain.ResultMeta;
import com.app.config.CatProperties;
import com.mybatis.pojo.Filetable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
@Controller
public class FileSysController extends BaseClass {
    @Autowired
    private FileSysServiceImpl fileSysServiceImpl;

    @Autowired
    private CatProperties catProperties;

    @GetMapping(value = "file")
    public ModelAndView init(){
        ModelAndView view = new ModelAndView();
        view.addObject(Measure.head_Authorization,catProperties.getUserid());
        return view;
    }

    @GetMapping(value = "file/sys/{pid}")
    @ResponseBody
    public List<Filetable> queryFileToTables(@PathVariable String pid){
        if (StringUtils.isBlank(pid)) return null;
        return fileSysServiceImpl.queryFileToTables(pid);
    }
    @RequestMapping(value = "file/sys", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultMeta>  createFloderToTables(@RequestBody Filetable model, HttpServletRequest request){
        if (StringUtils.isBlank(model.getPid())){
            model.setPid(catProperties.getUserid());
        }
        model.setUserid(catProperties.getUsername());
        model.setUrlname(catProperties.getUrl());
        model.setCreatetime(new Date());

        List<Filetable> list = queryFileToTables(model.getPid());
        String message = "";
        boolean suc = true;
        if (StringUtils.isBlank(model.getFilename())){
            message = "创建名称不能为空";
            suc = false;
        }
        for(Filetable table : list){
            if ( table.equals(model.getFilename())){
                message = "创建名称不能重复";
                suc = false;
                break;
            }
        }
        if (suc){
            suc = fileSysServiceImpl.saveOneFileToSysDisk(model);
            if (!suc){
                message = "创建失败";
            }
        }
        ResultMeta meta = new ResultMeta();
        if (suc){
            meta.setMessage("创建成功"+model.toString());
            return new ResponseEntity<ResultMeta>(meta.success(), HttpStatus.OK);
        }else{
            meta.setMessage(message+model.toString());
            return new ResponseEntity<ResultMeta>(meta.failure(), HttpStatus.OK);
        }
    }
    @RequestMapping(value = "file/sys/put", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultMeta> updateFloderOrFileName(@RequestBody Filetable model){
        int cont = fileSysServiceImpl.updateForModel(model);
        ResultMeta meta = new ResultMeta();
        if (cont>0){
            return new ResponseEntity<ResultMeta>(meta.success(), HttpStatus.OK);
        }else{
            return new ResponseEntity<ResultMeta>(meta.failure(), HttpStatus.OK);
        }
    }
}
