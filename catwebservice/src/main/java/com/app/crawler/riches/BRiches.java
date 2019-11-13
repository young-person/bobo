package com.app.crawler.riches;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.app.crawler.CrawlerDown;
import com.app.crawler.CrawlerMain;
import com.app.crawler.request.DefaultRestRequest;
import com.app.crawler.request.RestRequest;
import com.app.crawler.request.RestRequestFactory;
import com.app.crawler.riches.pojo.HistoryBean;
import com.app.crawler.riches.pojo.HistoryResult;
import com.app.crawler.riches.pojo.RicheBean;
import com.app.crawler.riches.pojo.RicheResult;
import com.app.crawler.riches.pojo.ShareInfo;
import com.app.crawler.riches.producer.Producer.CallBack;
import com.app.runner.ApplicationRunnerImpl;
import com.bobo.domain.ResultMeta;

public class BRiches implements CrawlerDown {

	public static final Map<String, Boolean> CODESTATUS = new ConcurrentHashMap<String, Boolean>();

	/**
	 * Excel 数据模板路径地址
	 */
	private String dataPath = ApplicationRunnerImpl.CAT_CACHE.get("dataPath").getValue();

	/**
	 * 平安证券
	 */
	private String stockAppUrl = "https://m.stock.pingan.com/h5quote/quote/getReportData";

	/**
	 * 股票类型
	 */
	final Map<String, String> typeName = new HashMap<String, String>() {
		private static final long serialVersionUID = 6893069965305064089L;

		{
			put("4621", "创业");
			put("4614", "深证");
			put("4353", "沪A");
			put("4609", "深A");
		}
	};
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
	 * 日线
	 */
	private String dayUrl = "https://m.stock.pingan.com/h5quote/quote/getDayDataBigInt?random=0.5947266470674488&thirdAccount=&rdm=&timestamp=&tokenId=&signature=&key=&chnl=&requestId=&stockCode=%s&codeType=%s&period=day&day=%s";

	/**
	 * 东方财富今日数据
	 */
	private String dfUrl = "http://25.push2.eastmoney.com/api/qt/clist/get?cb=jQuery112402546766279164878_1573279192452&pn=1&pz=5000&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:0+t:6,m:0+t:13,m:0+t:80,m:1+t:2,m:1+t:23&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f26,f22,f11,f62,f128,f136,f115,f152&_=1573279192548";

	/**
	 * 跳转到平安证券详情页面
	 */
	private String detailUrl = "https://m.stock.pingan.com/html/h5security/quote/detail.html?stock_code=%s&code_type=%s&type=shsz";

	/**
	 * arg[0] 股票代码 arg[1] 年份 arg[2] 季度 网易股票历史数据
	 */
	private String wyUrl = "http://quotes.money.163.com/trade/lsjysj_%s.html?year=%s&season=%s";

	private RicheCompute richeCompute;

	public RicheCompute getRicheCompute() {
		return richeCompute;
	}

	public void setRicheCompute(RicheCompute richeCompute) {
		this.richeCompute = richeCompute;
	}

	/**
	 * 开始计算 excel数据
	 *
	 * @param callBack
	 */
	public void calculate(CallBack<RicheTarget> callBack) throws IOException, URISyntaxException {
		LOGGER.info("开始股市数据计算任务");
		boolean sure = this.multiplexThead();
		if (!sure) {
			return;
		}

		File file = ResourceUtils.getFile(dataPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		for (File f : file.listFiles()) {
			if (!f.isDirectory()) {
				continue;
			}
			for (File e : f.listFiles()) {
				InputStream inputStream = null;
				Workbook workbook = null;
				try {
					inputStream = new FileInputStream(e);
					workbook = WorkbookFactory.create(inputStream);
					Sheet sheet = workbook.getSheetAt(0);
					int lastNum = sheet.getLastRowNum();
					List<ShareInfo> datas = new ArrayList<>();
					String hand = null;
					String risePrice = null;
					for (int k = 1; k <= lastNum; k++) {
						Row row = sheet.getRow(k);
						if (k == 1) {
							hand = row.getCell(1).getStringCellValue();
							risePrice = row.getCell(2).getStringCellValue();
						}
						ShareInfo info = new ShareInfo(row.getCell(0).getStringCellValue(),
								row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue(),
								row.getCell(3).getStringCellValue(), row.getCell(4).getStringCellValue(),
								row.getCell(5).getStringCellValue(), row.getCell(6).getStringCellValue(),
								row.getCell(7).getStringCellValue(), row.getCell(8).getStringCellValue(),
								row.getCell(9).getStringCellValue());
						datas.add(info);
					}
					String name = e.getName();
					String[] arr = name.replace(".xlsx", "").split("_");

					RicheTarget target = richeCompute.compute(datas, arr[1]);
					CODESTATUS.put(arr[0], true);

					target.setStockName(arr[1]);
					target.setHand(hand);
					target.setCode(arr[0]);
					target.setRisePrice(risePrice);
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
				} catch (IOException e1) {
					e1.printStackTrace();
				}finally {
					if (Objects.nonNull(inputStream)) {
						inputStream.close();
					}
				}
			}

		}
		sendData();
		LOGGER.info("结束此次股市数据计算任务");
	}

	private void sendData() {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/x-www-form-urlencoded");
			MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
			postParameters.add("datas", CODESTATUS);
			HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(postParameters, headers);
			RestRequest restTemplate = RestRequestFactory.createRestRequest(new DefaultRestRequest());
			ResponseEntity<ResultMeta> result = restTemplate.postForEntity(
					new URI(ApplicationRunnerImpl.CAT_CACHE.get("sendEmailAddress").getValue()), request,
					ResultMeta.class);
			LOGGER.info("将数据发送给远程结果：【{}】", result);
		} catch (Exception e) {
			LOGGER.error("发送远程失败");
		}
	}

	@Override
	public void start() {
		LOGGER.info("开始抓取股市数据");
		synchronized (BRiches.class) {

			Map<String, String> handMap = new HashMap<>();
			try {
				Map<String, File> resultMap = this.sureMkdirFolder();
				/**
				 * 基于平安银行数据获取 适用于IP没被加入到黑名单的时候 当开始的时候 进行模拟轮询 100次的ping 平安银行接口
				 */
				RicheResult handResult = getAllRicheBeans();

				/**
				 * 支持东方财富数据今日获取 适用于增量数据
				 */
				if ("false".equals(ApplicationRunnerImpl.CAT_CACHE.get("request").getValue())) {

					List<HistoryBean> datas = new ArrayList<>();
					List<RicheBean> list = new ArrayList<>();
					parseContentData(datas, list, handResult);

					if (resultMap.size() == 0) {
						this.getHistoryDataByWy(list);
					} else {
						// 进行动态添加 今日数据写入
						for (int index = 0; index < list.size(); index++) {
							try {
								this.insertDataByExcel(list.get(index), Arrays.asList(datas.get(index)));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				} else {

					for (RicheBean bean : handResult.getResults()) {
						handMap.put(bean.getCode(), bean.getHand());
					}
					RicheResult result = this.getTodayDatas();
					/**
					 * 获取所有股票
					 */
					for (RicheBean bean : result.getResults()) {
						if (!Arrays.asList("4353", "4614", "4609").contains(bean.getCodeType())) {
							continue;
						}
						bean.setHand(handMap.get(bean.getCode()));
					}
					this.requestData(result.getResults(), resultMap);
				}

			} catch (RestClientException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		LOGGER.info("结束此次抓取股市数据任务");
	}

	/**
	 * 通过网易抓取历史数据
	 *
	 * @param list
	 */
	private void getHistoryDataByWy(List<RicheBean> list) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		String year = dateFormat.format(new Date());
		Integer start = 1990;
		Integer end = Integer.valueOf(year);
//        ThreadPoolExecutor executor = CrawlerMain.newThreadPool("crawlerRicheExcel", 8);
		for (int index = 0; index < list.size(); index++) {
			RicheBean richeBean = list.get(index);
//            executor.execute(() -> {
			List<HistoryBean> beans = new ArrayList<>();
			for (int k = start; k <= end; k++) {
				for (int j = 1; j <= 4; j++) {
					String url = String.format(wyUrl, richeBean.getCode(), k, j);
					LOGGER.info("股票代码：【{}】,获取历史数据URL：【{}】", richeBean.getCode(), url);
					RestRequest restTemplate = RestRequestFactory.createRestRequest(new DefaultRestRequest());
					try {
						ResponseEntity<String> result = restTemplate.getForEntity(new URI(url), String.class);
						String content = result.getBody();
						Document bs = Jsoup.parse(content);
						Elements data = bs.select("table.table_bg001").select(".border_box").select(".limit_sale")
								.select("tbody");
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
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				}
			}
			writeHistoryDataToExcel(richeBean, beans);
//            });
		}
//        executor.shutdown();
	}

	/**
	 * 解析网易数据
	 *
	 * @param datas
	 * @param list
	 * @param handResult
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private void parseContentData(List<HistoryBean> datas, List<RicheBean> list, RicheResult handResult)
			throws IOException, URISyntaxException {
		Map<String, RicheBean> map = new HashMap<>();
		for (RicheBean bean : handResult.getResults()) {
			map.put(bean.getCode(), bean);
		}

		RestRequest restTemplate = RestRequestFactory.createRestRequest(new DefaultRestRequest());
		ResponseEntity<String> dfResult = restTemplate.getForEntity(new URI(dfUrl), String.class);
		String result = dfResult.getBody();

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

		JSONObject object = JSONObject.parseObject(builder.toString());
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
	 * 将今日数据插入到Excel
	 *
	 * @param bean
	 * @param datas
	 */
	private void insertDataByExcel(RicheBean bean, List<HistoryBean> datas) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			if (Objects.nonNull(datas) && datas.size() > 0) {
				File file = this.getExcelPath(bean);
				if (!file.exists()) {
					// 如果不存在 则是新数据 需要重新创建和分类
					getHistoryDataByWy(Arrays.asList(bean));
					return;
				}
				inputStream = new FileInputStream(file);
				Workbook workbook = WorkbookFactory.create(inputStream);
				Sheet sheet = workbook.getSheetAt(0);
				int first = sheet.getFirstRowNum();
				Row preDayRow = sheet.getRow(first + 1);

				String v1 = preDayRow.getCell(1).getStringCellValue();
				String v2 = preDayRow.getCell(2).getStringCellValue();
				String v3 = preDayRow.getCell(3).getStringCellValue();
				String v4 = preDayRow.getCell(4).getStringCellValue();
				String v6 = preDayRow.getCell(6).getStringCellValue();
				String v7 = preDayRow.getCell(7).getStringCellValue();
				String v8 = preDayRow.getCell(8).getStringCellValue();
				String v9 = preDayRow.getCell(9).getStringCellValue();
				if (datas.size() > 0) {
					HistoryBean b = datas.get(0);
					if (v1.equals(b.getHand()) && v2.equals(b.getRisePrice()) && v3.equals(b.getOpenPrice())
							&& v4.equals(b.getClosePrice()) && v6.equals(b.getMaxPrice()) && v7.equals(b.getMinPrice())
							&& v8.equals(b.getTotal()) && v9.equals(b.getMoney())) {
						return;
					}
				}

				String prePrivce = preDayRow.getCell(4).getStringCellValue();
				sheet.shiftRows(first + 1, sheet.getLastRowNum(), datas.size());

				for (int i = 0; i < datas.size(); i++) {
					datas.get(i).setHand(bean.getHand());
					if (StringUtils.isBlank(datas.get(i).getRisePrice())) {
						datas.get(i).setRisePrice("0");
					}
					Row row = sheet.createRow(i + 1 + first);
					this.writeValueToCell(row, datas.get(i), prePrivce);
				}

				outputStream = new FileOutputStream(this.getExcelPath(bean));
				workbook.write(outputStream);
			} else {
				this.writeHistoryData(bean);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(outputStream);
			IOUtils.closeQuietly(inputStream);
		}
	}

	/**
	 * 历史数据写入Excel
	 *
	 * @param bean
	 * @param datas
	 */
	private void writeHistoryDataToExcel(RicheBean bean, List<HistoryBean> datas) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			if (Objects.nonNull(datas) && datas.size() > 0) {
				datas.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
				inputStream = new FileInputStream(new File(ResourceUtils.getFile(dataPath), "share.xlsx"));
				Workbook workbook = WorkbookFactory.create(inputStream);
				workbook.setSheetName(0, bean.getCode());
				Sheet sheet = workbook.getSheetAt(0);
				for (int i = 0; i < datas.size(); i++) {
					sheet.createRow(i + 1);
					Row row = sheet.getRow(i + 1);
					HistoryBean historyBean = datas.get(i);
					String prePrivce = i == (datas.size() - 1) ? "0" : datas.get(i + 1).getClosePrice();
					this.writeValueToCell(row, historyBean, prePrivce);
				}
				outputStream = new FileOutputStream(this.getExcelPath(bean));
				workbook.write(outputStream);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(outputStream);
			IOUtils.closeQuietly(inputStream);
		}
	}

	/**
	 * 平安银行数据请求
	 *
	 * @param list
	 * @param resultMap
	 */
	private void requestData(List<RicheBean> list, Map<String, File> resultMap) {
		/**
		 * 获取所有股票
		 */
		ThreadPoolExecutor executor = CrawlerMain.newThreadPool("crawlerRicheExcel", 8);
		for (RicheBean bean : list) {
			if (!Arrays.asList("4353", "4614", "4609").contains(bean.getCodeType())) {
				continue;
			}
			executor.execute(() -> mindTrend(bean, resultMap));
			CODESTATUS.put(bean.getCode(), true);
		}
		executor.shutdown();
	}

	/**
	 * 平安银行数据历史数据全部写入
	 *
	 * @param bean
	 */
	private void writeHistoryData(RicheBean bean) {
		String url = String.format(dayUrl, bean.getCode(), bean.getCodeType(), 18000);
		LOGGER.info("股票：【{}】,获取历史数据URL：【{}】", bean.getStockName(), url);
		try {
			RestTemplate restTemplate = new RestTemplate();
			HistoryResult historyResult = restTemplate.getForObject(new URI(url), HistoryResult.class);
			this.writeHistoryDataToExcel(bean, historyResult.getResults());

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 平安银行历史数据和今日数据写入
	 *
	 * @param bean
	 * @param resultMap
	 */
	public void mindTrend(RicheBean bean, Map<String, File> resultMap) {
		if (resultMap.size() == 0) {
			this.writeHistoryData(bean);
		} else {
			// 进行动态添加 今日数据写入
			String url = String.format(dayUrl, bean.getCode(), bean.getCodeType(), 1);
			LOGGER.info("股票：【{}】,获取今日数据URL：【{}】", bean.getStockName(), url);
			try {
				RestTemplate restTemplate = new RestTemplate();
				HistoryResult historyResult = restTemplate.getForObject(new URI(url), HistoryResult.class);
				this.insertDataByExcel(bean, historyResult.getResults());
			} catch (RestClientException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 换手率 平安银行
	 *
	 * @throws URISyntaxException
	 * @throws RestClientException
	 */
	private RicheResult getTurnoverRate() throws RestClientException, URISyntaxException {
		Map<String, String> param = new HashMap<>();
		param.putAll(commonMap);
		param.put("columnId", "hand");
		param.put("descOrAsc", "desc");
		param.put("count", "5000");
		param.put("begin", "0");
		param.put("marketType", "shsz");

		String url = this.getUrl(param, stockAppUrl);
		LOGGER.info("换手率请求URL:[{}]", url);
		RestRequest restTemplate = RestRequestFactory.createRestRequest(new DefaultRestRequest());
		ResponseEntity<RicheResult> handResult = restTemplate.getForEntity(new URI(url), RicheResult.class);

		if (Objects.isNull(handResult)) {
			return null;
		}
		return handResult.getBody();
	}

	/**
	 * 获取所有股票今日数据 平安银行
	 *
	 * @throws URISyntaxException
	 * @throws RestClientException
	 */
	private RicheResult getTodayDatas() throws RestClientException, URISyntaxException {
		Map<String, String> param = new HashMap<>();
		param.putAll(commonMap);
		param.put("columnId", "risePrice");
		param.put("descOrAsc", "desc");
		param.put("count", "5000");
		param.put("begin", "0");
		param.put("marketType", "shsz");

		String url = this.getUrl(param, stockAppUrl);
		LOGGER.info("获取所有股票今日数据请求URL:[{}]", url);
		RestRequest restTemplate = RestRequestFactory.createRestRequest(new DefaultRestRequest());
		ResponseEntity<RicheResult> handResult = restTemplate.getForEntity(new URI(url), RicheResult.class);

		return handResult.getBody();
	}

	/**
	 * 查看是否第一次请求
	 *
	 * @return
	 */
	private Map<String, File> sureMkdirFolder() throws FileNotFoundException {
		File file = ResourceUtils.getFile(dataPath);
		LOGGER.info("文件路径为：【{}】", file.getAbsolutePath());
		if (!file.exists()) {
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
	private RicheResult getAllRicheBeans() throws URISyntaxException, IOException {
		synchronized (BRiches.class) {
			RicheResult handResult = getTurnoverRate();
			if (Objects.isNull(handResult)) {
				File pFile = ResourceUtils.getFile(dataPath);
				handResult = JSON.parseObject(new FileInputStream(new File(pFile, "archive.json")), RicheResult.class);
			}
			if (Objects.nonNull(handResult)) {
				FileOutputStream os = new FileOutputStream(new File(ResourceUtils.getFile(dataPath), "archive.json"));
				JSON.writeJSONString(os, handResult, SerializerFeature.QuoteFieldNames);

				for (RicheBean bean : handResult.getResults()) {
					CODESTATUS.put(bean.getCode(), false);
				}
			}
			return handResult;
		}
	}

	private void writeValueToCell(Row row, HistoryBean historyBean, String prePrivce) {
		row.createCell(0).setCellValue(historyBean.getDate());
		row.createCell(1).setCellValue(historyBean.getHand());
		row.createCell(2).setCellValue(historyBean.getRisePrice());
		row.createCell(3).setCellValue(historyBean.getOpenPrice());
		row.createCell(4).setCellValue(historyBean.getClosePrice());
		row.createCell(5).setCellValue(prePrivce);
		row.createCell(6).setCellValue(historyBean.getMaxPrice());
		row.createCell(7).setCellValue(historyBean.getMinPrice());
		row.createCell(8).setCellValue(historyBean.getTotal());
		row.createCell(9).setCellValue(historyBean.getMoney());
		row.createCell(10).setCellValue(historyBean.getRiseMoney());
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

	private File getExcelPath(RicheBean bean) throws FileNotFoundException {

		StringBuilder builder = new StringBuilder();
		builder.append(bean.getCode());
		builder.append("_");

		char p = '*';
		for (int index = 0; index < bean.getStockName().length(); index++) {
			if (p != bean.getStockName().charAt(index)) {
				builder.append(bean.getStockName().charAt(index));
			}
		}

		builder.append(".xlsx");
		File file = ResourceUtils.getFile(dataPath);

		File f1 = new File(file, this.typeName.get(bean.getCodeType()));
		if (!f1.exists()) {
			LOGGER.info("创建excel文件---->[{}.{}]", f1.getAbsolutePath(), builder.toString());
			f1.mkdirs();
		}
		File f2 = new File(f1, builder.toString());
		return f2;
	}

	/**
	 * 根据所有加载的数据来判断 是否文件可以被进行读了
	 *
	 * @return
	 */
	private boolean multiplexThead() throws IOException, URISyntaxException {
		synchronized (BRiches.class) {
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
		synchronized (BRiches.class) {
			try {
				boolean sure = false;
				RicheResult result = getAllRicheBeans();
				for (String key : CODESTATUS.keySet()) {
					if (CODESTATUS.get(key)) {
						sure = CODESTATUS.get(key);
						break;
					}
				}
				return sure && CODESTATUS.size() == result.getResults().size();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public String executeTimeFormat() {
		return null;
	}

	public String getDataPath() {
		return dataPath;
	}

	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}

}
