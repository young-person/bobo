package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.riches.pojo.RicheTarget;
import com.app.service.ReceiveRiches;
import com.bobo.domain.ResultMeta;

@Controller
public class IndexController {
	@Autowired
	private ReceiveRiches receiveRiches;
//    @Autowired
//    private LoginActionFeign loginService;
//
//    @RequestMapping(value = {"/upload/{views}/{name}"})
//    public ModelAndView toPage(@PathVariable(value="name")String name, @PathVariable(value="views")String views){
//        List<SysMenu> menus = loginService.queryListMenu("0");
//
//        ModelMap model = new ModelMap();
//
//        List<Bean>  beans = TreeUtils.buildListTree(menus);
//        model.put("beans",beans);
//
//        StringBuilder builder = new StringBuilder();
//        if (StringUtils.isNotBlank(views)){
//            builder.append(views);
//            builder.append("/");
//            builder.append(name);
//        }else{
//            builder.append(name);
//        }
//        return new ModelAndView(builder.toString(), model);
//    }
	
	@PostMapping(value = "richesData")
	@ResponseBody
	public ResponseEntity<ResultMeta> receiveRichesData(@RequestBody List<RicheTarget> datas){
		receiveRiches.receiveRichesData(datas);
		
		return new ResponseEntity<ResultMeta>(HttpStatus.OK);
	}
}
