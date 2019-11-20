package com.app.crawler.riches;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.app.crawler.base.CrawlerDown;
import com.app.crawler.base.RCache;
import com.app.crawler.request.RestRequest;
import com.app.crawler.riches.pojo.*;
import com.app.crawler.riches.producer.Persist;
import com.app.crawler.riches.producer.PersistExcel;
import com.app.crawler.riches.producer.PersistTxt;
import com.app.crawler.riches.producer.Producer.CallBack;
import org.apache.poi.util.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BRiches extends BRichesBase implements CrawlerDown {

	private RestRequest request = new RestRequest();

	public static final Map<String, Boolean> CODESTATUS = new ConcurrentHashMap<String, Boolean>();

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

	/**
	 * 平安证券
	 */
	private String stockAppUrl = "https://m.stock.pingan.com/h5quote/quote/getReportData";

	/**
	 * 日线
	 */
	private String dayUrl = "https://m.stock.pingan.com/h5quote/quote/getDayDataBigInt?random=0.5947266470674488&thirdAccount=&rdm=&timestamp=&tokenId=&signature=&key=&chnl=&requestId=&stockCode=%s&codeType=%s&period=day&day=%s";

	/**
	 * 跳转到平安证券详情页面
	 */
	private String detailUrl = "https://m.stock.pingan.com/html/h5security/quote/detail.html?stock_code=%s&code_type=%s&type=shsz";

	/**
	 * 东方财富今日数据
	 */
	private String dfUrl = "http://25.push2.eastmoney.com/api/qt/clist/get?cb=jQuery112402546766279164878_1573279192452&pn=1&pz=5000&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:0+t:6,m:0+t:13,m:0+t:80,m:1+t:2,m:1+t:23&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f26,f22,f11,f62,f128,f136,f115,f152&_=1573279192548";

	/**
	 * 所有指标数据项
	 */
	private String allUrl = "http://25.push2.eastmoney.com/api/qt/clist/get?cb=jQuery112402546766279164878_1573279192452&pn=1&pz=5000&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:0+t:6,m:0+t:13,m:0+t:80,m:1+t:2,m:1+t:23&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11,f12,f13,f14,f15,f16,f17,f18,f19,f20,f21,f22,f23,f24,f25,f26,f27,f28,f29,f30,f31,f32,f33,f34,f35,f36,f37,f38,f39,f40,f41,f42,f43,f44,f45,f46,f47,f48,f49,f50,f51,f52,f53,f54,f55,f56,f57,f58,f59,f60,f61,f62,f63,f64,f65,f66,f67,f68,f69,f70,f71,f72,f73,f74,f75,f76,f77,f78,f79,f80,f81,f82,f83,f84,f85,f86,f87,f88,f89,f90,f91,f92,f93,f94,f95,f96,f97,f98,f99,f100,f101,f102,f103,f104,f105,f106,f107,f108,f109,f110,f111,f112,f113,f114,f115,f116,f117,f118,f119,f120,f121,f122,f123,f124,f125,f126,f127,f128,f129,f130,f131,f132,f133,f134,f135,f136,f137,f138,f139,f140,f141,f142,f143,f144,f145,f146,f147,f148,f149,f150,f151,f152,f153,f154,f155,f156,f157,f158,f159,f160,f161,f162,f163,f164,f165,f166,f167,f168,f169,f170,f171,f172,f173,f174,f175,f176,f177,f178,f179,f180,f181,f182,f183,f184,f185,f186,f187,f188,f189,f190,f191,f192,f193,f194,f195,f196,f197,f198,f199,f200,f201,f202,f203,f204,f205,f206,f207,f208,f209,f210,f211,f212,f213,f214,f215,f216,f217,f218,f219,f220,f221,f222,f223,f224,f225,f226,f227,f228,f229,f230,f231,f232,f233,f234,f235,f236,f237,f238,f239,f240,f241,f242,f243,f244,f245,f246,f247,f248,f249,f250,f251,f252,f253,f254,f255,f256,f257,f258,f259,f260,f261,f262,f263,f264,f265,f266,f267,f268,f269,f270,f271,f272,f273,f274,f275,f276,f277,f278,f279,f280,f281,f282,f283,f284,f285,f286,f287,f288,f289,f290,f291,f292,f293&_=1573279192548";

	/**
	 * arg[0] 股票代码 arg[1] 年份 arg[2] 季度 网易股票历史数据
	 */
	private String wyUrl = "http://quotes.money.163.com/trade/lsjysj_%s.html?year=%s&season=%s";

	private RicheCompute richeCompute;

	public void setRicheCompute(RicheCompute richeCompute) {
		this.richeCompute = richeCompute;
	}

	/**
	 * 开始计算 excel数据
	 *
	 * @param callBack
	 */
	public void calculate(CallBack<RicheTarget> callBack) throws IOException, IllegalAccessException, InvocationTargetException {
		LOGGER.info("开始股市数据计算任务");
		boolean sure = this.multiplexThead();
		if (!sure) {
			return;
		}
		File file = new File(dataPath);
		for (File f : file.listFiles()) {
			if (!f.isDirectory()) {
				continue;
			}
			for (File e : f.listFiles()) {
				List<HistoryBean> datas = persist.readHistoryFromFile(e);
				String name = e.getName();
				String[] arr = name.replace(".xlsx", "").split("_");
				RicheTarget target = richeCompute.compute(convertHistory(datas), arr[1]);
				CODESTATUS.put(arr[0], true);
				String code_type = null;
				for (String key : typeName.keySet()) {
					if (typeName.get(key).equals(f.getName())) {
						code_type = key;
						break;
					}
				}
				target.setDetailUrl(String.format(detailUrl, arr[0], code_type));
				if (Objects.nonNull(callBack) && !"创业".equals(f.getName())) {
					callBack.add(target);
				}
			}
		}
		sendData();
		LOGGER.info("结束此次股市数据计算任务");
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

	private void sendData() {
//		try {
//			HttpHeaders headers = new HttpHeaders();
//			headers.add("Content-Type", "application/x-www-form-urlencoded");
//			MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
//			postParameters.add("datas", CODESTATUS);
//			HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(postParameters, headers);
//			RestRequest restTemplate = RestRequestFactory.createRestRequest(new DefaultRestRequest());
//			ResponseEntity<ResultMeta> result = restTemplate.postForEntity(
//					new URI(RCache.CAT_CACHE.get("sendEmailAddress").getValue()), request,
//					ResultMeta.class);
//			LOGGER.info("将数据发送给远程结果：【{}】", result);
//		} catch (Exception e) {
//			LOGGER.error("发送远程失败");
//		}
	}

	private Persist persist = new PersistTxt();

	@Override
	public void start() {
		LOGGER.info("开始抓取股市数据");
		try {
			this.sureMkdirFolder();
			RicheResult handResult = getAllRicheBeans();

			/**
			 * 支持东方财富数据今日获取 适用于增量数据
			 */
//			List<HistoryBean> datas = new ArrayList<>();
//			List<RicheBean> list = new ArrayList<>();
//			parseContentData(datas, list, handResult);
			this.getHistoryDataByWy(handResult.getResults());//每次都全量
		} catch (IOException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			LOGGER.error("抓取出现错误", e);
		}
		LOGGER.info("结束此次抓取股市数据任务");
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
		for (int index = 0; index < list.size(); index++) {
			RicheBean richeBean = list.get(index);
			List<HistoryBean> beans = new ArrayList<>();
			for (int k = start; k <= end; k++) {
				for (int j = 1; j <= 4; j++) {
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
				}
			}
			persist.writeHistoryDataToFile(richeBean, beans);
		}
		this.writeAllTipData(today);
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
		String allResult = request.doGet(allUrl);

		JSONObject object = JSONObject.parseObject(this.trimJquery(allResult).toString());

		JSONArray array = object.getJSONArray("data");

		File file = new File(RCache.CAT_CACHE.get("dataPath").getValue() +today+ ".json");
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		JSON.writeJSONString(new FileOutputStream(file), array, SerializerFeature.QuoteFieldNames);
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
		File file = new File(dataPath);
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
		Map<String, File> resultMap = new HashMap<String, File>(file.listFiles().length);
		for (File f : file.listFiles()) {
			if (!f.isDirectory()) {
				continue;
			}
			for (File e : f.listFiles()) {
				resultMap.put(e.getName(), f);
			}
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
		File pFile = new File(dataPath);
		if (!pFile.exists()) {
			pFile.mkdirs();
		}
		if (Objects.isNull(handResult)) {

			handResult = JSON.parseObject(new FileInputStream(new File(pFile, "archive.json")), RicheResult.class);
		}
		if (Objects.nonNull(handResult)) {
			File file = new File(pFile, "archive.json");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream os = null;
			try {
				os = new FileOutputStream(file);
				JSON.writeJSONString(os, handResult, SerializerFeature.QuoteFieldNames);

				for (RicheBean bean : handResult.getResults()) {
					CODESTATUS.put(bean.getCode(), false);
				}
				os.flush();
			} finally {
				IOUtils.closeQuietly(os);
			}

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
	/**
	 * 根据所有加载的数据来判断 是否文件可以被进行读了
	 *
	 * @return
	 */
	private boolean multiplexThead() throws IOException {
		RicheResult result = this.getAllRicheBeans();
		while (true) {
			int size = 0;
			for (String key : CODESTATUS.keySet()) {
				if (!CODESTATUS.get(key)) {
					size++;
				}
			}
			if (size == CODESTATUS.size() && CODESTATUS.size() == result.getResults().size()) {
				break;
			}
		}
		return true;
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
		try {
			boolean sure = false;
			RicheResult result = getAllRicheBeans();
			for (String key : CODESTATUS.keySet()) {
				if (CODESTATUS.get(key)) {
					sure = CODESTATUS.get(key);
					break;
				}
			}
			LOGGER.info("是否正在运行：{}",sure && CODESTATUS.size() == result.getResults().size());
			return sure && CODESTATUS.size() == result.getResults().size();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String executeTimeFormat() {
		return null;
	}

}
