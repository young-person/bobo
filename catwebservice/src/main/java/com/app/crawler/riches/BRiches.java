package com.app.crawler.riches;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.app.crawler.base.CrawlerDown;
import com.app.crawler.pojo.Property;
import com.app.crawler.pojo.RichesData;
import com.app.crawler.request.RestRequest;
import com.app.crawler.riches.pojo.*;
import com.app.crawler.riches.producer.Persist;
import com.app.crawler.riches.producer.PersistTxt;
import com.app.crawler.riches.producer.RLoadXml;
import org.apache.poi.util.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;

public class BRiches extends BRichesBase implements CrawlerDown {

	private RestRequest request = new RestRequest();

	private static AtomicBoolean ISRUN = new AtomicBoolean(false);
	private static AtomicBoolean ISCALCULATE = new AtomicBoolean(false);
	/**
	 * 存放所有指标数据
	 */
	public static final Map<String, RichesData> TIPMAP = new ConcurrentHashMap<>();
	/**
	 * 通用请求参数数据
	 */
	private Map<String, String> commonMap = new HashMap<String, String>() {
		private static final long serialVersionUID = -6545149106686379462L;
		{
			put("random", "0.35715587574136154");
			put("thirdAccount", "");
			put("rdm", "");
			put("timestamp", "");
			put("tokenId", "");
			put("signature", "");
			put("key", "");
			put("chnl", "");
			put("requestId", "");

			put("type", "shsz");
		}
	};

	private RicheComputeAbstract richeCompute = new RicheComputeAbstract();;


	/**
	 * 均衡值
	 */
	private final float avg = 0.5f;

	private final float hand = 0.5f;

	private static final CopyOnWriteArraySet<RicheTarget> RICHETARGET = new CopyOnWriteArraySet<RicheTarget>();

	public static CopyOnWriteArraySet<RicheTarget> get() {
		return RICHETARGET;
	}


	/**
	 * 开始计算 excel数据
	 */
	public void calculate(Integer limit,Integer num) {
		LOGGER.info("开始股市数据计算任务,计算状态；【{}】",isRuning());
		if (isRuning()) {
			return;
		}
		ISCALCULATE.set(true);
		if (Objects.isNull(limit) || limit == 0)
			limit= 7;
		if (Objects.isNull(num) || num == 0)
			num = 30;
		richeCompute.setLimit(limit);
		richeCompute.setNum(num);

		File file = null;
		try {
			file = ResourceUtils.getFile(catXml.getDataPath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		RICHETARGET.clear();
		for (File f : file.listFiles()) {
			if (!f.isDirectory() || !typeName.containsValue(f.getName())) {
				continue;
			}
			for (File e : f.listFiles()) {
				try{
					LOGGER.info("开始读取文件{}里面的数据转出对应对象",file.getName());
					List<HistoryBean> datas = persist.readHistoryFromFile(e);
					String name = e.getName();
					String[] arr = name.replace(".txt", "").split("_");
					LOGGER.info("开始计算{}文件里面的数据",name);
					RicheTarget target = richeCompute.compute(convertHistory(datas));
					String code_type = null;
					for (String key : typeName.keySet()) {
						if (typeName.get(key).equals(f.getName())) {
							code_type = key;
							break;
						}
					}
					target.setDetailUrl(String.format(detailUrl, arr[0], code_type));
					target.setCode(arr[0]);
					target.setStockName(arr[1]);
					if (Objects.nonNull(TIPMAP.get(arr[0]))){
						target.setPrice(TIPMAP.get(arr[0]).getF1());
						target.setType(TIPMAP.get(arr[0]).getF100());
					}else{
						LOGGER.error("{}不存在于TIPMAP里面，可能此数据集已被下架不需要再被显示",arr[0]);
						continue;
					}

					LOGGER.info("完成计算{}文件里面的数据，结果集为：【{}】",name,target);
					if (!"创业".equals(f.getName())) {
						if (Float.valueOf(target.getHand()) >= hand && !target.getStockName().contains("ST") && target.getL() > avg) {
							RICHETARGET.add(target);
						}else if(Float.valueOf(target.getHand()) >= hand && target.getStockName().contains("ST")) {
							LOGGER.trace("ST系列：----->{}",JSONObject.toJSONString(target));
						}
					}
				} catch (IllegalAccessException | IOException | InvocationTargetException ex) {
					ex.printStackTrace();
				}
			}
		}
		LOGGER.info("结束此次股市数据计算任务");
		ISCALCULATE.set(false);
	}

	private List<ShareInfo> convertHistory(List<HistoryBean> datas){
		List<ShareInfo> result = new ArrayList<>(datas.size());
		for(int index = 0; index < datas.size(); index ++){
			HistoryBean bean = datas.get(index);
			ShareInfo info = new ShareInfo();
			info.setDate(bean.getDate());
			info.setHand(bean.getHand());
			info.setRisePrice(bean.getRisePrice());
			info.setOpenPrice(bean.getOpenPrice());
			info.setClosePrice(bean.getClosePrice());

			info.setMaxPrice(bean.getMaxPrice());
			info.setMinPrice(bean.getMinPrice());
			info.setTotal(bean.getTotal());
			info.setMoney(bean.getMoney());
			if (index == 0){
				info.setPrevClose(bean.getClosePrice());
			}else {
				info.setPrevClose(datas.get(index - 1).getClosePrice());
			}
			result.add(info);
		}
		return result;
	}

	private Persist persist = new PersistTxt();

	@Override
	public void start() {
		LOGGER.info("开始抓取股市数据");
		ISRUN.set(true);
		try {
			this.writeAllTipData();
			this.sureMkdirFolder();
			RicheResult handResult = getAllRicheBeans();

			/**
			 * 支持东方财富数据今日获取 适用于增量数据
			 */
			this.getHistoryDataByWy(handResult.getResults());
		} catch (IOException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			LOGGER.error("抓取出现错误", e);
		}
		LOGGER.info("结束此次抓取股市数据任务");
		ISRUN.set(false);
		this.calculate(null,null);
		initList();

	}

	private Map<String,JSONObject> loadStartToEnd(List<RicheBean> list) throws IOException {
		File file = ResourceUtils.getFile(catXml.getDataRoot());
		File pFile = new File(file,"history");
		if (!pFile.exists()){
			pFile.mkdirs();
		}
		File allTime = new File(pFile,"time.json");
		if(!allTime.exists()){
			allTime.createNewFile();
		}
		byte[] b = IOUtils.toByteArray(new FileInputStream(allTime));
		JSONArray array = null;
		if (Objects.nonNull(b) && b.length > 0){
			array = JSONArray.parseArray(new String(b));
		}else{
			array = new JSONArray();
		}

		Map<String,JSONObject> dataMap = new HashMap<>();
		if (Objects.nonNull(array)){
			for(int index = 0; index < array.size();index ++){
				JSONObject o = array.getJSONObject(index);
				dataMap.put(o.getString("code"),o);
			}
		}else{
			array = new JSONArray();
		}

		for(RicheBean bean:list){

			if (dataMap.containsKey(bean.getCode()))
				continue;
			String start = "1990";
			String end = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			JSONObject object = new JSONObject();
			object.put("code",bean.getCode());
			object.put("start",start);
			object.put("end",end);
			array.add(object);
			dataMap.put(bean.getCode(),object);
		}
		JSON.writeJSONString(new FileOutputStream(allTime), array, SerializerFeature.QuoteFieldNames);
		return dataMap;
	}

	/**
	 * 进行监听数据初始化
	 */
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

		RLoadXml loadXml = new RLoadXml();

		Data data = loadXml.getDataFromXml(catXml.getDataPath());
		if (Objects.nonNull(data.getBeans())){
			for(int index = 0; index < data.getBeans().size(); index ++){
				Bean bean1 = data.getBeans().get(index);
				filter.add(bean1.getCode());
			}
		}
		String s1 = null, s2 = null;
		for (File f : file.listFiles()) {
			if (!f.isDirectory() || !typeName.containsValue(f.getName())) {
				continue;
			}
			for (File e : f.listFiles()) {
				try{
					List<HistoryBean> datas = persist.readHistoryFromFile(e);
					if (Objects.isNull(datas) || datas.size() == 0){
						continue;
					}
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
					//将最新的数据写入到xml中
					RichesData bean = getChooice(arr[0]);
					List<Property> list = loadXml.converClassToModel(bean);
					if (filter.contains(arr[0]) || "创业".equals(f.getName())){
						if (!"创业".equals(f.getName())){
							for(Bean b :data.getBeans()){
								b.setProperties(list);
							}
						}
						continue;
					}

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
	public RichesData getChooice(String code){
		for(RichesData bean : TIPMAP.values()){
			if (bean.getF12().equals(code) || bean.getF14().equals(code)){
				return bean;
			}
		}
		JSONArray array = this.getAllTips();
		List<RichesData> datas = JSON.parseArray(array.toJSONString(), RichesData.class);
		RichesData result = null;
		for(RichesData bean:datas){
			if (bean.getF12().equals(code) || bean.getF14().equals(code)){
				return bean;
			}
		}
		return result;
	}
	/**
	 * 通过网易抓取历史数据
	 *
	 * @param list
	 */
	private void getHistoryDataByWy(List<RicheBean> list) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

		Map<String,JSONObject> dataMap = this.loadStartToEnd(list);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		String year = dateFormat.format(new Date());

		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		String now = format.format(date);

		String month = now.substring(4);
		int quarter = Integer.valueOf(month) / 3 + 1;

		for (int index = 0; index < list.size(); index++) {
			RicheBean richeBean = list.get(index);
			//判断当前是否存在
			File file = this.getExcelPath(richeBean,".txt");
			List<HistoryBean> beans = new ArrayList<>();
			if (file.exists()){
				this.writeData(richeBean,list,index,Integer.valueOf(year),quarter,beans);
				if (beans.size()>0){
					beans = beans.subList(0, 1);
				}
			}else{
				JSONObject object = dataMap.get(richeBean.getCode());
				int start = Integer.valueOf(object.getString("start"));
				int end  =  Integer.valueOf(object.getString("end"));

				for (int k = start; k <= end; k++) {
					for (int j = 1; j <= 4; j++) {
						this.writeData(richeBean,list,index,k,j,beans);
					}
				}
			}
			Collections.sort(beans, Comparator.comparingInt(o -> Integer.parseInt(o.getDate())));
			persist.writeHistoryDataToFile(richeBean, beans);
		}
	}

	private void writeData(RicheBean richeBean,List<RicheBean> list,int index,int k, int j,List<HistoryBean> beans){
		String url = String.format(wyUrl, richeBean.getCode(), k, j);
		LOGGER.info("股票代码：【{}】,获取历史数据URL：【{}】，总共有【{}】行，已经抓取到第【{}】行", richeBean.getCode(), url,list.size(),index);
		String content = request.doGet(url);
		Document bs = Jsoup.parse(content);
		Elements data = bs.select("table.table_bg001").select(".border_box").select(".limit_sale").select("tbody");
		if (Objects.nonNull(data)) {
			Elements trs = data.select("tr");
			for (Element element : trs) {
				Elements tds = element.select("td");
				HistoryBean historyBean = new HistoryBean();
				historyBean.setDate(tds.get(0).text().replace("-", ""));
				historyBean.setOpenPrice(tds.get(1).text());
				historyBean.setMaxPrice(tds.get(2).text());
				historyBean.setMinPrice(tds.get(3).text());
				historyBean.setClosePrice(tds.get(4).text());
				historyBean.setRiseMoney(tds.get(5).text());
				historyBean.setRisePrice(tds.get(6).text());
				historyBean.setTotal(tds.get(7).text());
				historyBean.setMoney(tds.get(8).text());
				historyBean.setHand(tds.get(10).text());
				beans.add(historyBean);
			}
		}
	}

	public OnlineBean reloadOnlineData(String code){
		LOGGER.info("加载当前实时数据地址：[{}]",String.format(onlineUrl,code,codeTypeMap.get(code)));
		String content = request.doGet(String.format(onlineUrl,code,codeTypeMap.get(code)));
		OnlineResult result = JSONObject.parseObject(content,OnlineResult.class);
		result.getResults().setCode(code);
		return result.getResults();
	}

	/**
	 * 去除jquery前面字符串获取json对象
	 *
	 * @param result
	 * @return
	 * @Description: trimJquery
	 * @date 2019年11月16日 上午11:22:31
	 */
	private StringBuilder trimJquery(String result) {
		StringBuilder builder = new StringBuilder();
		boolean flag = false;
		for (int k = 0; k < result.length() - 2; k++) {
			char v = result.charAt(k);
			char _v = '(';
			if (flag) {
				builder.append(v);
			}
			if (_v == v && !flag) {
				flag = true;
			}
		}
		return builder;
	}

	/**
	 * 将所有指标数据写入excel
	 *
	 * @Description: writeAllTipData
	 * @date 2019年11月16日 上午11:27:49
	 */
	private void writeAllTipData() throws IOException {
		JSONArray array = this.getAllTips();
		String path = catXml.getTipsDataPath();
		File f = ResourceUtils.getFile(path);
		String day = new StringBuilder(Calendar.getInstance().get(Calendar.DATE)).append(".json").toString();
		File file = new File(f,day);
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		JSON.writeJSONString(new FileOutputStream(file), array, SerializerFeature.QuoteFieldNames);
	}

	public JSONArray getAllTips(){
		LOGGER.info("获取【{}】今日所有指标数据：【{}】",Calendar.getInstance().get(Calendar.DATE),allUrl);
		String allResult = null;
		for(int index = 1; index < 10; index ++){
			if (Objects.nonNull(allResult))
				break;
			try {
				allResult = request.doGet(allUrl);
			}catch (Exception e){
				LOGGER.error("第{}次获取全部指标失败",index,e);
			}
		}

		JSONObject object = JSONObject.parseObject(this.trimJquery(allResult).toString());

		JSONArray array = object.getJSONObject("data").getJSONArray("diff");
		List<RichesData> datas = JSON.parseArray(array.toJSONString(), RichesData.class);
		LOGGER.info("开始写入数据到TIPMAP");
		for(RichesData r :datas){
			TIPMAP.put(r.getF12(),r);
		}
		LOGGER.info("结束写入数据到TIPMAP：写入数据个数:【{}】",datas.size());
		return object.getJSONObject("data").getJSONArray("diff");
	}

	/**
	 * 换手率 平安银行
	 *
	 */
	private RicheResult getTurnoverRate() {
		Map<String, String> param = new HashMap<>();
		param.putAll(commonMap);
		param.put("columnId", "hand");
		param.put("descOrAsc", "desc");
		param.put("count", "5000");
		param.put("begin", "0");
		param.put("marketType", "shsz");

		String url = this.getUrl(param, stockAppUrl);
		LOGGER.info("换手率请求URL:[{}]", url);

		String content = request.doGet(url);
		if (Objects.isNull(content)) {
			return null;
		}
		return JSONObject.parseObject(content, RicheResult.class);
	}


	/**
	 * 查看是否第一次请求
	 *
	 * @return
	 */
	private Map<String, File> sureMkdirFolder() {
		Map<String, File> resultMap = new HashMap<String, File>();
		try {
			File file = ResourceUtils.getFile(catXml.getDataPath());

			if (!file.exists()) {
				LOGGER.info("第一次请求文件路径为：【{}】", file.getAbsolutePath());
				file.mkdirs();
			}
			typeName.forEach((k, v) -> {
				boolean flag = true;
				for (File f : file.listFiles()) {
					if (f.getName().contentEquals(v)) {
						flag = false;
					}
				}
				if (flag) {
					File childFile = new File(file, v);
					if (!childFile.exists()) {
						childFile.mkdir();
					}
				}
			});

			for (File f : file.listFiles()) {
				if (!f.isDirectory()) {
					continue;
				}
				for (File e : f.listFiles()) {
					resultMap.put(e.getName(), f);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 需要请求数据进行初始化
	 *
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	private RicheResult getAllRicheBeans() throws IOException {
		RicheResult handResult = getTurnoverRate();
		File pFile = ResourceUtils.getFile(catXml.getDataPath());
		if (!pFile.exists()) {
			pFile.mkdirs();
		}
		if (Objects.isNull(handResult)) {
			handResult = JSON.parseObject(new FileInputStream(new File(pFile, catXml.getArchive())), RicheResult.class);
		}
		if (Objects.nonNull(handResult)) {
			File file = new File(pFile, catXml.getArchive());
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream os = null;
			try {
				os = new FileOutputStream(file);
				JSON.writeJSONString(os, handResult, SerializerFeature.QuoteFieldNames);
				os.flush();
			} finally {
				IOUtils.closeQuietly(os);
			}

		}
		for(RicheBean r : handResult.getResults()){
			codeTypeMap.put(r.getCode(),r.getCodeType());
		}
		return handResult;
	}



	private String getUrl(Map<String, String> parmas, String url) {
		StringBuilder builder = new StringBuilder(url);
		boolean flag = true;
		if (Objects.nonNull(parmas)) {
			for (String key : parmas.keySet()) {
				if (flag) {
					builder.append("?");
					flag = false;
				} else {
					builder.append("&");
				}
				builder.append(key);
				builder.append("=");
				builder.append(parmas.get(key));
			}
		}
		return builder.toString();
	}

	@Override
	public boolean stop() {
		return false;
	}

	@Override
	public boolean suspend() {
		return false;
	}

	@Override
	public boolean isRuning() {
		return ISRUN.get();
	}

	@Override
	public String executeTimeFormat() {
		return null;
	}

}
