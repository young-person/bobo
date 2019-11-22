package com.app.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.app.config.CatXml;
import com.app.crawler.base.RCache;
import com.app.crawler.pojo.Property;
import com.app.crawler.pojo.RName;
import com.app.crawler.pojo.RichesData;
import com.app.crawler.riches.BRiches;
import com.app.crawler.riches.pojo.Bean;
import com.app.crawler.riches.pojo.Data;
import com.app.crawler.riches.pojo.ExcelUser;
import com.app.crawler.riches.producer.Producer;
import com.app.crawler.riches.producer.RLoadXml;
import com.app.service.impl.ReceiveRichesImpl;
import com.bobo.base.BaseClass;
import com.bobo.domain.ResultMeta;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping(value = "riche")
public class RicheController extends BaseClass{

	private CatXml catXml = new CatXml();
	
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
		StringBuilder buffer = new StringBuilder();
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

	/**
	 * 存在就删除
	 * @param code
	 * @return
	 */
	@GetMapping(value = "deleteStock/{code}")
	public ResponseEntity<ResultMeta> deleteListenerStock(@PathVariable String code){
		RLoadXml loadXml = new RLoadXml();
		ResultMeta meta = new ResultMeta();
		try{
			Data data = loadXml.getDataFromXml(catXml.getDataPath());
			for(Bean bean1 :data.getBeans()){
				if (bean1.getCode().equals(code)){
					data.getBeans().remove(bean1);
					break;
				}
			}
			loadXml.convertToXml(data,catXml.getDataPath());
			meta.isSuccess();
		}catch (Exception e){
			meta.failure();
		}
		return new ResponseEntity<>(meta,HttpStatus.OK);
	}

	/**
	 * 添加一个需要监听得数据指标
	 * @param code
	 * @param value
	 * @return
	 */
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
			b.setCode(bean.getF12());
			Data data = loadXml.getDataFromXml(catXml.getDataPath());
			boolean flag = true;
			for(Bean bean1 :data.getBeans()){
				if (bean1.getCode().equals(code)){
					flag = false;
					break;
				}
			}
			if (flag){
				data.getBeans().add(b);
			}
			loadXml.convertToXml(data,catXml.getDataPath());

			meta.isSuccess();
		} catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			e.printStackTrace();
			meta.failure();
		}
		return new ResponseEntity<>(meta,HttpStatus.OK);
	}

	/**
	 * 获取状态类型
	 * @return
	 */
	@GetMapping(value = "findStock")
	public ResponseEntity<ResultMeta> findListenerStock(){
		ResultMeta meta = new ResultMeta();
		RLoadXml loadXml = new RLoadXml();
		Data data = loadXml.getDataFromXml(catXml.getDataPath());
		List<Bean> beans = data.getBeans();
		try {
			JSONArray array = new JSONArray();
			Field[] fields = RichesData.class.getFields();
			List<String> headers = new ArrayList<>();
			for(Field field : fields){
				field.getName();
				RName rName = field.getAnnotation(RName.class);
				if (Objects.nonNull(rName)){
					String name = rName.value();
					headers.add(name);
				}
			}
			for(Bean b : beans){
				RichesData richesData = new RichesData();
				richesData = loadXml.convertModelToClass(richesData,b.getProperties());
				JSONObject object = new JSONObject();

				String[] mark = b.getMark().split("-");
				Float preV = Float.valueOf(richesData.getF1());
				Float minV = Float.valueOf(mark[0]);
				Float maxV = Float.valueOf(mark[1]);
				int status = 0;
				if (preV >= minV && preV <= maxV){
					status = 1;
				}else if(preV > maxV){
					status = 2;
				}
				object.put("code", b.getCode());
				object.put("name",b.getName());
				object.put("mark",b.getMark());
				object.put("bean",richesData);
				object.put("status",status);
				array.add(object);
			}
			JSONObject r = new JSONObject();
			r.put("headers",headers);
			r.put("datas",array);
			meta.success(r);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			meta.failure();
		}
		return new ResponseEntity<>(meta,HttpStatus.OK);
	}
	@GetMapping(value = "findUser")
	public ModelAndView findUsers(){
		ModelAndView view = new ModelAndView("users");
		ReceiveRichesImpl receiveRiches = new ReceiveRichesImpl();
		List<ExcelUser> users = receiveRiches.getAllUsers();
		view.addObject("datas", users);
		return view;
	}

	@PostMapping(value = "addUser")
	public ModelAndView findUsers(@RequestBody ExcelUser user){
		ResultMeta meta = new ResultMeta();
		ReceiveRichesImpl receiveRiches = new ReceiveRichesImpl();
		List<ExcelUser> users = receiveRiches.getAllUsers();

		boolean flag = true;
		for(ExcelUser excelUser :users){
			if (excelUser.getAccount().equals(user.getAccount())){
				//更新
				excelUser.setId(UUID.randomUUID().toString());
				excelUser.setEmail(user.getEmail());
				excelUser.setForbidden(user.getForbidden());
				excelUser.setUserName(user.getUserName());
				excelUser.setPassWord(user.getPassWord());
				flag =false;
				break;
			}
		}
		if (flag){
			users.add(user);
		}
		meta.success(users);
		ModelAndView view = new ModelAndView("redirect:findUser");
		return view;
	}

	@GetMapping(value = "deleteUser/{id}")
	public ModelAndView findUsers(@PathVariable String id){
		ResultMeta meta = new ResultMeta();
		ReceiveRichesImpl receiveRiches = new ReceiveRichesImpl();
		List<ExcelUser> users = receiveRiches.getAllUsers();
		for(ExcelUser excelUser :users){
			if (excelUser.getId().equals(id)){
				users.remove(excelUser);
				break;
			}
		}
		File pFile = new File(RCache.CAT_CACHE.get("dataPath").getValue());
		FileOutputStream stream = null;
		try {
			File file = new File(pFile, "users.json");
			stream = new FileOutputStream(file);
			JSON.writeJSONString(stream, users, SerializerFeature.QuoteFieldNames);
			meta.success(users);
		} catch (IOException e) {
			e.printStackTrace();
			meta.failure();
		} finally {
			IOUtils.closeQuietly(stream);
		}
		ModelAndView view = new ModelAndView("redirect:findUser");
		return view;
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
