package com.app.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.app.crawler.base.RCache;
import com.app.crawler.pojo.Property;
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
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
			Data data = loadXml.getDataFromXml(RCache.CAT_CACHE.get("dataPath").getValue());
			for(Bean bean1 :data.getBeans()){
				if (bean1.getCode().equals(code)){
					data.getBeans().remove(bean1);
					break;
				}
			}
			loadXml.convertToXml(data,RCache.CAT_CACHE.get("dataPath").getValue());
			meta.isSuccess();
		}catch (Exception e){
			meta.failure();
		}

		return new ResponseEntity<>(meta,HttpStatus.OK);
	}

	@GetMapping(value = "addStock/{type}")
	public ResponseEntity<ResultMeta> getTypeStock(@PathVariable String type){
		ResultMeta meta = new ResultMeta();

		File file = new File(RCache.CAT_CACHE.get("tipsDataPath").getValue());

		String tmpName = null;
		for(File f : file.listFiles()){
			if(Objects.isNull(tmpName)){
				tmpName = f.getName();
			}else{

				if (tmpName.compareTo(f.getName()) <= 0){
					tmpName = f.getName();
				}
			}
		}
		List<RichesData> r = new ArrayList<>();
		if (Objects.nonNull(tmpName)){
			File f = new File(file,tmpName);
			InputStream inputStream = null;
			try {
				inputStream = new FileInputStream(f);
				List<RichesData> data = JSON.parseObject(inputStream, RichesData.class);
				for(RichesData richesData :data){
					if (richesData.getF100().indexOf(type) > -1 || richesData.getF103().indexOf(type) > -1){
						r.add(richesData);
					}
				}
				meta.success(JSONArray.toJSON(r));
			} catch (IOException e) {
				e.printStackTrace();
				meta.failure();
			}finally {
				IOUtils.closeQuietly(inputStream);
			}
		}
		return new ResponseEntity<>(meta,HttpStatus.OK);
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
			b.setCode(bean.getF12());
			Data data = loadXml.getDataFromXml(RCache.CAT_CACHE.get("dataPath").getValue());
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
			loadXml.convertToXml(data,RCache.CAT_CACHE.get("dataPath").getValue());

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
		Data data = loadXml.getDataFromXml(RCache.CAT_CACHE.get("dataPath").getValue());
		List<Bean> beans = data.getBeans();
		try {

			JSONArray array = new JSONArray();
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
			meta.success(array);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			meta.failure();
		}
		return new ResponseEntity<>(meta,HttpStatus.OK);
	}
	@GetMapping(value = "findUser")
	public ResponseEntity<ResultMeta> findUsers(){
		ResultMeta meta = new ResultMeta();
		ReceiveRichesImpl receiveRiches = new ReceiveRichesImpl();
		List<ExcelUser> users = receiveRiches.getAllUsers();
		meta.success(users);
		return new ResponseEntity<>(meta,HttpStatus.OK);
	}

	@PostMapping(value = "addUser")
	public ResponseEntity<ResultMeta> findUsers(@RequestBody ExcelUser user){
		ResultMeta meta = new ResultMeta();
		ReceiveRichesImpl receiveRiches = new ReceiveRichesImpl();
		List<ExcelUser> users = receiveRiches.getAllUsers();

		boolean flag = true;
		for(ExcelUser excelUser :users){
			if (excelUser.getAccount().equals(user.getAccount())){
				//更新
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
		return new ResponseEntity<>(meta,HttpStatus.OK);
	}

	@GetMapping(value = "deleteUser/{id}")
	public ResponseEntity<ResultMeta> findUsers(@PathVariable String id){
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
