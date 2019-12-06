package com.app.crawler.riches;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.app.crawler.base.CrawlerDown;
import com.app.crawler.request.RestRequest;
import com.app.crawler.riches.pojo.*;
import com.app.crawler.riches.producer.Persist;
import com.app.crawler.riches.producer.PersistExcel;
import com.app.crawler.riches.producer.PersistTxt;
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
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;

public class BRiches extends BRichesBase implements CrawlerDown {

	private RestRequest request = new RestRequest();

	private static AtomicBoolean ISRUN = new AtomicBoolean(false);
	private static AtomicBoolean ISCALCULATE = new AtomicBoolean(false);

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
		if (isRuning() || ISCALCULATE.get()) {
			return;
		}
		ISCALCULATE.set(true);
		if (Objects.isNull(limit) || limit == 0)
			limit= 30;
		if (Objects.isNull(num) || num == 0)
			num = 7;
		richeCompute.setLimit(limit);
		richeCompute.setNum(num);

		File file = null;
		try {
			file = ResourceUtils.getFile(catXml.getDataPath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (File f : file.listFiles()) {
			if (!f.isDirectory()) {
				continue;
			}
			for (File e : f.listFiles()) {
				try{
					List<HistoryBean> datas = persist.readHistoryFromFile(e);
					String name = e.getName();
					String[] arr = name.replace(".txt", "").split("_");
					RicheTarget target = richeCompute.compute(convertHistory(datas), arr[1]);
					String code_type = null;
					for (String key : typeName.keySet()) {
						if (typeName.get(key).equals(f.getName())) {
							code_type = key;
							break;
						}
					}
					target.setDetailUrl(String.format(detailUrl, arr[0], code_type));
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
		for(HistoryBean bean : datas){
			ShareInfo info = new ShareInfo();
			info.setDate(bean.getDate());
			info.setHand(bean.getHand());
			info.setRisePrice(bean.getRisePrice());
			info.setOpenPrice(bean.getOpenPrice());
			info.setClosePrice(bean.getClosePrice());
			info.setPrevClose("");
			info.setMaxPrice(bean.getMaxPrice());
			info.setMinPrice(bean.getMinPrice());
			info.setTotal(bean.getTotal());
			info.setMoney(bean.getMoney());
		}
		return result;
	}

	private Persist persist = new PersistTxt();

	@Override
	public void start() {
		LOGGER.info("开始抓取股市数据");
		ISRUN.set(true);
		try {
			this.sureMkdirFolder();
			RicheResult handResult = getAllRicheBeans();

			/**
			 * 支持东方财富数据今日获取 适用于增量数据
			 */
			this.getHistoryDataByWy(handResult.getResults());//每次都全量
		} catch (IOException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			LOGGER.error("抓取出现错误", e);
		}
		LOGGER.info("结束此次抓取股市数据任务");
		ISRUN.set(false);
		this.calculate(null,null);

	}

	private void loadStartToEnd(List<RicheBean> list){
		Map<String,File> map = this.sureMkdirFolder();
		for(RicheBean bean:list){
			String url = String.format(simpleUrl, bean.getCode());
			String result = this.trimJquery(request.doGet(url)).toString();

			DFData data = JSONObject.parseObject(result,DFData.class);
			String start = data.getStart();
			String end = data.getEnd();

		}

	}

	/**
	 * 通过网易抓取历史数据
	 *
	 * @param list
	 */
	private void getHistoryDataByWy(List<RicheBean> list) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		String year = dateFormat.format(new Date());
		Integer start = 1990;
		Integer end = Integer.valueOf(year);
		String today = "0";

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
				today = this.writeData(richeBean,list,index,Integer.valueOf(year),quarter,beans,today);
				if (beans.size()>0){
					beans = beans.subList(0, 1);
				}
			}else{
				for (int k = start; k <= end; k++) {
					for (int j = 1; j <= 4; j++) {
						today = this.writeData(richeBean,list,index,k,j,beans,today);
					}
				}
			}
			Collections.sort(beans, Comparator.comparingInt(o -> Integer.parseInt(o.getDate())));
			persist.writeHistoryDataToFile(richeBean, beans);
		}
		this.writeAllTipData(today);
	}

	private String writeData(RicheBean richeBean,List<RicheBean> list,int index,int k, int j,List<HistoryBean> beans,String today){
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
				if (Integer.valueOf(historyBean.getDate()) > Integer.valueOf(today)){
					today = historyBean.getDate();
				}
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
		return today;
	}

	public OnlineBean reloadOnlineData(String code){
		LOGGER.info("加载当前实时数据地址：[{}]",String.format(onlineUrl,code,codeTypeMap.get(code)));
		String content = request.doGet(String.format(onlineUrl,code,codeTypeMap.get(code)));
		OnlineResult result = JSONObject.parseObject(content,OnlineResult.class);
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
	 * 通过东方财富网获取今日数据指标
	 *
	 * @param datas
	 * @param list
	 * @param handResult
	 */
	private void parseContentData(List<HistoryBean> datas, List<RicheBean> list, RicheResult handResult){
		Map<String, RicheBean> map = new HashMap<>();
		for (RicheBean bean : handResult.getResults()) {
			map.put(bean.getCode(), bean);
		}
		LOGGER.info("通过东方财富网获取今日数据指标：[{}]",dfUrl);
		String result = request.doGet(dfUrl);

		JSONObject object = JSONObject.parseObject(this.trimJquery(result).toString());
		JSONObject dataObject = object.getJSONObject("data");
		JSONArray array = dataObject.getJSONArray("diff");

		for (int index = 0; index < array.size(); index++) {
			JSONObject obj = array.getJSONObject(index);
			String hand = obj.getString("f8");
			String risePrice = obj.getString("f24");
			String openPrice = obj.getString("f17");
			String closePrice = obj.getString("f2");
			String riseMoney = obj.getString("f4");
			String maxPrice = obj.getString("f15");
			String minPrice = obj.getString("f16");
			String total = obj.getString("f5");
			String money = obj.getString("f6");

			String name = obj.getString("f14");
			String code = obj.getString("f12");

			String date = obj.getString("f26");

			HistoryBean historyBean = new HistoryBean();
			historyBean.setDate(date);
			historyBean.setHand(hand);
			historyBean.setRisePrice(risePrice);
			historyBean.setOpenPrice(openPrice);
			historyBean.setClosePrice(closePrice);
			historyBean.setMaxPrice(maxPrice);
			historyBean.setMinPrice(minPrice);
			historyBean.setTotal(total);
			historyBean.setMoney(money);
			historyBean.setRiseMoney(riseMoney);

			if (Objects.nonNull(map.get(code))) {
				RicheBean bean = new RicheBean();
				bean.setHand(hand);
				bean.setCode(code);
				bean.setStockName(name);
				bean.setCodeType(map.get(code).getCodeType());
				bean.setNewPrice(closePrice);
				bean.setTotalAmount(total);
				bean.setRisePrice(risePrice);
				datas.add(historyBean);
				list.add(bean);
			}
		}
	}

	/**
	 * 平安银行数据历史数据全部写入
	 *
	 * @param bean
	 */
	private void writeHistoryData(RicheBean bean) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		String url = String.format(dayUrl, bean.getCode(), bean.getCodeType(), 18000);
		LOGGER.info("股票：【{}】,获取历史数据URL：【{}】", bean.getStockName(), url);
		String content = request.doGet(url);
		HistoryResult historyResult = JSONObject.parseObject(content, HistoryResult.class);
		Persist persist = new PersistExcel();
		persist.writeHistoryDataToFile(bean, historyResult.getResults());
	}

	/**
	 * 将所有指标数据写入excel
	 *
	 * @Description: writeAllTipData
	 * @date 2019年11月16日 上午11:27:49
	 */
	private void writeAllTipData(String today) throws IOException {
		JSONArray array = this.getAllTips();

		File f = new File(catXml.getTipsDataPath());
		if (!f.exists()){
			f.mkdirs();
		}

		File file = new File(f, today+ ".json");
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		JSON.writeJSONString(new FileOutputStream(file), array, SerializerFeature.QuoteFieldNames);
	}

	public JSONArray getAllTips(){
		LOGGER.info("获取【{}】今日所有指标数据：【{}】",Calendar.getInstance().get(Calendar.DATE),allUrl);
		String allResult = request.doGet(allUrl);

		JSONObject object = JSONObject.parseObject(this.trimJquery(allResult).toString());

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
