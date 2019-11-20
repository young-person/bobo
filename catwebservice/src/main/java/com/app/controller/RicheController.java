package com.app.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.app.crawler.base.RCache;
import com.app.crawler.pojo.Property;
import com.app.crawler.pojo.RichesData;
import com.app.crawler.riches.BRiches;
import com.app.crawler.riches.pojo.Bean;
import com.app.crawler.riches.pojo.Data;
import com.app.crawler.riches.producer.Producer;
import com.app.crawler.riches.producer.RLoadXml;
import com.bobo.base.BaseClass;
import com.bobo.domain.ResultMeta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Controller
public class RicheController extends BaseClass{

	
	@RequestMapping(value = "view")
	public ModelAndView initView() {
		ModelAndView view = new ModelAndView("datashow");
		return view;
	}
	@GetMapping(value = "calculate")
	@ResponseBody
	public void queryWealthSource(@RequestParam(defaultValue = "21") Integer num,@RequestParam(defaultValue = "7") Integer limit) {
		Producer producer = new Producer();
		producer.repeatCalculate(limit, num);
	}
	@GetMapping(value = "start")
	@ResponseBody
	public String handToCrawlerRicheData() {
		StringBuffer buffer = new StringBuffer();
		try {
			BRiches bRiches = new BRiches();
			bRiches.start();
			buffer.append("触发数据抓取成功！");
		} catch (Exception e) {
			buffer.append("触发数据抓取失败!");
			LOGGER.error("触发数据抓取失败", e);
		}
		return buffer.toString();
	}

	@GetMapping(value = "addStock/{code}/{value}")
	public ResponseEntity<ResultMeta> addListenerStock(@PathVariable String code,@PathVariable String value){
		RichesData bean = getChooice(code);
		RLoadXml loadXml = new RLoadXml();

		ResultMeta meta = new ResultMeta();
		try {

			List<Property> list = loadXml.converClassToModel(bean);
			Bean b = new Bean();
			b.setName(bean.getF14());
			b.setMark(value);//监听值
			b.setProperties(list);
			Data data = loadXml.getDataFromXml(RCache.CAT_CACHE.get("dataPath").getValue());
			data.getBeans().add(b);
			loadXml.convertToXml(data,RCache.CAT_CACHE.get("dataPath").getValue());

			meta.isSuccess();
		} catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			e.printStackTrace();
			meta.failure();
		}
		return new ResponseEntity<>(meta,HttpStatus.OK);
	}
	@GetMapping(value = "findStock")
	public ResponseEntity<ResultMeta> findListenerStock(){
		ResultMeta meta = new ResultMeta();
		RLoadXml loadXml = new RLoadXml();
		Data data = loadXml.getDataFromXml(RCache.CAT_CACHE.get("dataPath").getValue());
		List<Bean> beans = data.getBeans();

		try {

			JSONArray array = new JSONArray();
			for(Bean b : beans){
				RichesData richesData = new RichesData();
				richesData = loadXml.convertModelToClass(richesData,b.getProperties());
				JSONObject object = new JSONObject();
				object.put("name",b.getName());
				object.put("mark",b.getMark());
				object.put("bean",richesData);
				object.put("status","");
			}
			meta.success(array);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			meta.failure();
		}
		return new ResponseEntity<>(meta,HttpStatus.OK);
	}
	private RichesData getChooice(String code){
		BRiches bRiches = new BRiches();
		JSONArray array = bRiches.getAllTips();

		List<RichesData> datas = JSON.parseArray(array.toJSONString(), RichesData.class);
		RichesData result = null;
		for(RichesData bean:datas){
			if (bean.getF12().equals(code)){
				return bean;
			}
		}
		return result;
	}
}
