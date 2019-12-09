package com.app.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.app.config.CatXml;
import com.app.crawler.pojo.Property;
import com.app.crawler.pojo.RName;
import com.app.crawler.pojo.RichesData;
import com.app.crawler.riches.BRiches;
import com.app.crawler.riches.pojo.Bean;
import com.app.crawler.riches.pojo.Data;
import com.app.crawler.riches.pojo.ExcelUser;
import com.app.crawler.riches.pojo.HistoryBean;
import com.app.crawler.riches.producer.Persist;
import com.app.crawler.riches.producer.PersistTxt;
import com.app.crawler.riches.producer.RLoadXml;
import com.app.service.impl.ReceiveRichesImpl;
import com.bobo.base.BaseClass;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static com.app.crawler.riches.BRiches.TIPMAP;

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
		BRiches briches = new BRiches();
		briches.calculate(limit, num);
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
	public ModelAndView deleteListenerStock(@PathVariable String code){
		ModelAndView view = new ModelAndView("redirect:/riche/findStock");
		RLoadXml loadXml = new RLoadXml();
		try{
			Data data = loadXml.getDataFromXml(catXml.getDataPath());
			if (Objects.nonNull(data.getBeans())){
				Iterator<Bean> iterator = data.getBeans().iterator();

				while(iterator.hasNext()){
					Bean bean1 = iterator.next();
					if (bean1.getCode().equals(code)){
						iterator.remove();
						break;
					}
				}
			}
			loadXml.convertToXml(data,catXml.getDataPath());
		}catch (Exception e){
			e.printStackTrace();
		}
		return view;
	}

	/**
	 * 初始化数据
	 */
	@GetMapping(value = "initStock")
	@ResponseBody
	public void initList(){
		int d = 50;//关于天的间隔
		int w = 380;//关于周的间隔

		File file = null;
		try {
			file = ResourceUtils.getFile(catXml.getDataPath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		List<String> filter = new ArrayList<>();

		Persist persist= new PersistTxt();
		RLoadXml loadXml = new RLoadXml();

		Data data = loadXml.getDataFromXml(catXml.getDataPath());
		if (Objects.nonNull(data.getBeans())){
			for(int index = 0; index < data.getBeans().size(); index ++){
				Bean bean1 = data.getBeans().get(index);
				filter.add(bean1.getCode());
			}
		}
		Map<String, String> typeName = new HashMap<String, String>() {
			private static final long serialVersionUID = 6893069965305064089L;

			{
				put("4621", "创业");
				put("4614", "深证");
				put("4353", "沪A");
				put("4609", "深A");
			}
		};
		String s1 = null, s2 = null;
		for (File f : file.listFiles()) {
			if (!f.isDirectory() || !typeName.containsValue(f.getName())) {
				continue;
			}
			for (File e : f.listFiles()) {
				try{
					List<HistoryBean> datas = persist.readHistoryFromFile(e);

					Collections.sort(datas, (o1, o2) -> o2.getDate().compareTo(o1.getDate()));
					List<HistoryBean> list1 = null;
					List<HistoryBean> list2 = null;
					if(datas.size() <= d){
						list1 = datas.subList(0,datas.size());
					}else if((datas.size() > d && datas.size() <= w)){
						list1 = datas.subList(0,d);
						list2 = datas.subList(0,datas.size());
					}else if(datas.size() > w){
						list1 = datas.subList(0,d);
						list2 = datas.subList(0,w);
					}
					Collections.sort(list1, Comparator.comparing(HistoryBean::getClosePrice));
					s1 = list1.get(0).getClosePrice().replace(",","");

					if (Objects.isNull(list2)){
						s2 = String.valueOf(Float.valueOf(s1.replace(",","")) * 1.05);
					}else{
						Collections.sort(list2, Comparator.comparing(HistoryBean::getClosePrice));
						s2 = list2.get(0).getClosePrice();
					}

					StringBuilder builder = new StringBuilder();
					if (Float.valueOf(s1.replace(",","")) >= Float.valueOf(s2.replace(",",""))){
						builder.append(s2.replace(",","")).append("-").append(s1.replace(",",""));
					}else{
						builder.append(s1.replace(",","")).append("-").append(s2.replace(",",""));
					}
					String name = e.getName();
					String[] arr = name.replace(".txt", "").split("_");
					if (filter.contains(arr[0]) || "创业".equals(f.getName())){
						continue;
					}
					RichesData bean = getChooice(arr[0]);
					List<Property> list = loadXml.converClassToModel(bean);
					Bean b = new Bean();
					b.setName(bean.getF14());
					b.setMark(builder.toString());//监听值
					b.setProperties(list);
					b.setCode(bean.getF12());
					b.setMacd("0");
					data.getBeans().add(b);

				} catch (IllegalAccessException | IOException | InvocationTargetException | NoSuchMethodException ex) {
					LOGGER.error("进行初始化监控数据失败,文件:{}",f.getName(),e);
				}
			}
		}
		loadXml.convertToXml(data,catXml.getDataPath());
	}
	/**
	 * 添加一个需要监听得数据指标  根据代码或者名称进行添加
	 * @param code
	 * @param value
	 * @param macd 是否为等待抄底
	 * @return
	 */
	@PostMapping(value = "addStock")
	public ModelAndView addListenerStock(String code,String value,@RequestParam(defaultValue = "0") String macd){
		RichesData bean = getChooice(code);
		RLoadXml loadXml = new RLoadXml();
		ModelAndView view = new ModelAndView("redirect:/riche/findStock");
		try {
			List<Property> list = loadXml.converClassToModel(bean);
			Bean b = new Bean();
			b.setName(bean.getF14());
			b.setMark(value);//监听值
			b.setProperties(list);
			b.setCode(bean.getF12());
			b.setMacd(macd);
			Data data = loadXml.getDataFromXml(catXml.getDataPath());
			boolean flag = true;
			if (Objects.nonNull(data.getBeans())){
				for(int index = 0; index < data.getBeans().size(); index ++){
					Bean bean1 = data.getBeans().get(index);
					if (bean1.getCode().equals(code)){
						flag = false;
						data.getBeans().set(index,b);
						break;
					}
				}
			}else{
				data.setBeans(new ArrayList<>());
			}
			if (flag){

				data.getBeans().add(b);
			}
			loadXml.convertToXml(data,catXml.getDataPath());
		} catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return view;
	}

	/**
	 * 获取状态类型
	 * @return
	 */
	@GetMapping(value = "findStock")
	public ModelAndView findListenerStock(){
		ModelAndView view = new ModelAndView("schedule");
		RLoadXml loadXml = new RLoadXml();
		Data data = loadXml.getDataFromXml(catXml.getDataPath());
		List<Bean> beans = data.getBeans();
		try {
			JSONArray array = new JSONArray();
			Field[] fields = RichesData.class.getDeclaredFields();
			List<Map<String,String>> headers = new ArrayList<>();
			for(Field field : fields){
				RName rName = field.getAnnotation(RName.class);
				if (Objects.nonNull(rName)){
					String name = rName.value();
					Map<String,String> map = new HashMap<>(2);
					map.put("name",name);
					map.put("field",field.getName());
					headers.add(map);
				}
			}
			if (Objects.nonNull(beans)){
				for(Bean b : beans){
					RichesData richesData = new RichesData();
					richesData = loadXml.convertModelToClass(richesData,b.getProperties());
					JSONObject object = new JSONObject();

					String[] mark = b.getMark().split("-");
					Float preV = Float.valueOf(richesData.getF1());
					Float minV = Float.valueOf(mark[0]);
					Float maxV = Float.valueOf(mark[1]);
					String status = "#f9f9f9";
					if (preV >= minV && preV <= maxV){
						status = "#b3b967";
					}else if(preV > maxV){
						status = "#dea4a4";
					}
					object.put("code", b.getCode());
					object.put("name",b.getName());
					object.put("mark",b.getMark());
					object.put("bean",richesData);
					object.put("status",status);
					array.add(object);
				}
			}
			view.addObject("headers",headers);
			view.addObject("datas",array);

		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return view;
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
	public ModelAndView findUsers(ExcelUser user){
		ReceiveRichesImpl receiveRiches = new ReceiveRichesImpl();

		try {
			List<ExcelUser> users = receiveRiches.getAllUsers();

			boolean flag = true;
			if (Objects.nonNull(users)){
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
			}else{
				users = new ArrayList<>();
			}
			if (flag){
				user.setId(UUID.randomUUID().toString());
				users.add(user);
			}
			receiveRiches.writeUserToJson(users);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ModelAndView view = new ModelAndView("redirect:/riche/findUser");
		return view;
	}

	@GetMapping(value = "deleteUser/{id}")
	public ModelAndView findUsers(@PathVariable String id){
		ReceiveRichesImpl receiveRiches = new ReceiveRichesImpl();
		List<ExcelUser> users = receiveRiches.getAllUsers();
		for(ExcelUser excelUser :users){
			if (excelUser.getId().equals(id)){
				users.remove(excelUser);
				break;
			}
		}
		File pFile = null;
		try {
			pFile = ResourceUtils.getFile(catXml.getDataPath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		FileOutputStream stream = null;
		try {
			File file = new File(pFile, catXml.getUsersPath());
			stream = new FileOutputStream(file);
			JSON.writeJSONString(stream, users, SerializerFeature.QuoteFieldNames);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(stream);
		}
		ModelAndView view = new ModelAndView("redirect:/riche/findUser");
		return view;
	}

	@GetMapping(value = "sendMail")
	public ResponseEntity<String> sendMail(){
		ReceiveRichesImpl receiveRiches = new ReceiveRichesImpl();
		receiveRiches.sendScheduleData();
		return new ResponseEntity("成功", HttpStatus.OK);
	}

	private RichesData getChooice(String code){
		for(RichesData bean : TIPMAP.values()){
			if (bean.getF12().equals(code) || bean.getF14().equals(code)){
				return bean;
			}
		}
		BRiches bRiches = new BRiches();
		JSONArray array = bRiches.getAllTips();
		List<RichesData> datas = JSON.parseArray(array.toJSONString(), RichesData.class);
		RichesData result = null;
		for(RichesData bean:datas){
			if (bean.getF12().equals(code) || bean.getF14().equals(code)){
				return bean;
			}
		}
		return result;
	}

}
