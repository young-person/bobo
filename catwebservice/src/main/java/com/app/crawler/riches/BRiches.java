package com.app.crawler.riches;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.IOUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.app.crawler.CrawlerDown;
import com.app.crawler.CrawlerMain;
import com.app.crawler.riches.pojo.HistoryBean;
import com.app.crawler.riches.pojo.HistoryResult;
import com.app.crawler.riches.pojo.RicheBean;
import com.app.crawler.riches.pojo.RicheResult;
import com.app.crawler.riches.pojo.ShareInfo;

public class BRiches implements CrawlerDown {

	/**
	 * Excel 数据模板路径地址
	 */
	private String dataPath = "C:\\env\\bobo\\catwebservice\\src\\main\\resources\\datas\\share";

	public static void main(String[] args) {

		BRiches bRiches = new BRiches();
		RicheComputeAbstract richeCompute = new RicheComputeAbstract();
		richeCompute.setNum(20);
		bRiches.setRicheCompute(richeCompute);
		bRiches.start();
	}

	/**
	 * 平安证券
	 */
	private String stockAppUrl = "https://m.stock.pingan.com/h5quote/quote/getReportData";

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

	/// restapi/servicecenter/option/homeIcon/customIconById
	/**
	 * 月线
	 */
	// https://m.stock.pingan.com/h5quote/quote/getDayDataBigInt?random=0.060092707195325445&thirdAccount=&rdm=&timestamp=&tokenId=&signature=&key=&chnl=&requestId=&stockCode=300797&codeType=4621&period=month&day=50
	/**
	 * 周线
	 */
	// https://m.stock.pingan.com/h5quote/quote/getDayDataBigInt?random=0.8903233738548277&thirdAccount=&rdm=&timestamp=&tokenId=&signature=&key=&chnl=&requestId=&stockCode=000917&codeType=4609&period=week&day=50
	/**
	 * 日线
	 */
	private String dayUrl = "https://m.stock.pingan.com/h5quote/quote/getDayDataBigInt?random=0.5947266470674488&thirdAccount=&rdm=&timestamp=&tokenId=&signature=&key=&chnl=&requestId=&stockCode=%s&codeType=%s&period=day&day=%s";

	private RicheCompute richeCompute;

	public RicheCompute getRicheCompute() {
		return richeCompute;
	}

	public void setRicheCompute(RicheCompute richeCompute) {
		this.richeCompute = richeCompute;
	}

	public void calculate() {

		File file = new File(this.dataPath);
		if (file.listFiles().length > 0) {
			ThreadPoolExecutor executor = CrawlerMain.newThreadPool("readRicheExcel", 8);

			for (File f : file.listFiles()) {
				if (f.isDirectory()) {
					for (File e : f.listFiles()) {
						executor.execute(new Runnable() {
							@Override
							public void run() {
								InputStream inputStream = null;
								Workbook workbook = null;
								try {
									inputStream = new FileInputStream(e);
									workbook = WorkbookFactory.create(inputStream);
									Sheet sheet = workbook.getSheetAt(0);
									int lastNum = sheet.getLastRowNum();
									List<ShareInfo> datas = new ArrayList<>(lastNum);
									for (int k = 0; k < lastNum; k++) {
										Row row = sheet.getRow(k);
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
								} catch (IOException e) {
									e.printStackTrace();
								} catch (InvalidFormatException e) {
									e.printStackTrace();
								}
							}
						});
					}
				}
			}
			executor.shutdown();
			while(executor.isTerminating()) {
				
			}
		}
	}

	RestTemplate restTemplate = new RestTemplate();

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
	 * 换手率
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
		RicheResult handResult = restTemplate.getForObject(new URI(url), RicheResult.class);

		return handResult;
	}

	/**
	 * 获取所有股票今日数据
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
		RicheResult handResult = restTemplate.getForObject(new URI(url), RicheResult.class);

		return handResult;
	}

	private Map<String, File> sureMkdirFolder() {
		File file = new File(this.dataPath);
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

	@Override
	public void start() {

		Map<String, File> resultMap = this.sureMkdirFolder();

		Map<String, String> handMap = new HashMap<>();

		try {
			RicheResult handResult = getTurnoverRate();

			for (RicheBean bean : handResult.getResults()) {
				handMap.put(bean.getCode(), bean.getHand());
			}
			RicheResult result = this.getTodayDatas();

			int index = 0;
			/**
			 * 获取所有股票
			 */
			for (RicheBean bean : result.getResults()) {
				if (!Arrays.asList("4353", "4614", "4609").contains(bean.getCodeType())) {
					continue;
				}
				if (index > 1) {
					break;
				}
				bean.setHand(handMap.get(bean.getCode()));
				this.mindTrend(bean, resultMap);

				index++;
			}
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private void writeHistoryData(RicheBean bean) {
		// 历史数据全部写入
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

	private void insertDataByExcel(RicheBean bean, List<HistoryBean> datas) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			if (Objects.nonNull(datas) && datas.size() > 0) {

				inputStream = new FileInputStream(new File(this.getExcelPath(bean)));
				Workbook workbook = WorkbookFactory.create(inputStream);
				Sheet sheet = workbook.getSheetAt(0);
				Row preDayRow = sheet.getRow(2);
				String prePrivce = preDayRow.getCell(4).getStringCellValue();
				sheet.shiftRows(2, sheet.getLastRowNum(), datas.size());

				for (int i = 0; i < datas.size(); i++) {

					Row row = sheet.createRow(i + 2);

					row.createCell(0).setCellValue(datas.get(i).getDate());
					row.createCell(1).setCellValue(bean.getHand());
					row.createCell(2).setCellValue(bean.getRisePrice());
					row.createCell(3).setCellValue(datas.get(i).getOpenPrice());
					row.createCell(4).setCellValue(datas.get(i).getClosePrice());
					row.createCell(5).setCellValue(prePrivce);
					row.createCell(6).setCellValue(datas.get(i).getMaxPrice());
					row.createCell(7).setCellValue(datas.get(i).getMinPrice());
					row.createCell(8).setCellValue(datas.get(i).getTotal());
					row.createCell(9).setCellValue(datas.get(i).getMoney());
				}

				outputStream = new FileOutputStream(new File(this.getExcelPath(bean)));
				workbook.write(outputStream);
			}else {
				this.writeHistoryData(bean);
			}

		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(outputStream);
			IOUtils.closeQuietly(inputStream);
		}
	}

	private void writeHistoryDataToExcel(RicheBean bean, List<HistoryBean> datas) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			if (Objects.nonNull(datas) && datas.size() > 0) {
				datas.sort(new Comparator<HistoryBean>() {
					@Override
					public int compare(HistoryBean o1, HistoryBean o2) {
						return o2.getDate().compareTo(o1.getDate());
					}
				});
				inputStream = new FileInputStream(new File(this.dataPath, "share.xlsx"));
				Workbook workbook = WorkbookFactory.create(inputStream);
				workbook.setSheetName(0, bean.getStockName());
				Sheet sheet = workbook.getSheetAt(0);
				for (int i = 0; i < datas.size(); i++) {
					sheet.createRow(i + 1);
					Row row = sheet.getRow(i + 1);
					row.createCell(0).setCellValue(datas.get(i).getDate());
					if (i == 0) {
						row.createCell(1).setCellValue(bean.getHand());
						row.createCell(2).setCellValue(bean.getRisePrice());
					} else {
						row.createCell(1).setCellValue("0");
						row.createCell(2).setCellValue("0");
					}
					row.createCell(3).setCellValue(datas.get(i).getOpenPrice());
					row.createCell(4).setCellValue(datas.get(i).getClosePrice());
					if (i == 0) {
						row.createCell(5).setCellValue("0");
					} else {
						row.createCell(5).setCellValue(datas.get(i - 1).getClosePrice());
					}
					row.createCell(6).setCellValue(datas.get(i).getMaxPrice());
					row.createCell(7).setCellValue(datas.get(i).getMinPrice());
					row.createCell(8).setCellValue(datas.get(i).getTotal());
					row.createCell(9).setCellValue(datas.get(i).getMoney());
				}

				outputStream = new FileOutputStream(new File(this.getExcelPath(bean)));
				workbook.write(outputStream);
			}

		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(outputStream);
			IOUtils.closeQuietly(inputStream);
		}
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

	private String getExcelPath(RicheBean bean) {
		StringBuilder builder = new StringBuilder(this.dataPath);
		builder.append(File.separatorChar);
		builder.append(this.typeName.get(bean.getCodeType()));
		builder.append(File.separatorChar);
		builder.append(bean.getCode());
		builder.append("_");
		builder.append(bean.getStockName());
		builder.append(".xlsx");
		LOGGER.info("创建excel文件---->[{}]", builder.toString());
		return builder.toString();
	}
	@Override
	public boolean stop() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean suspend() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRuning() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String executeTimeFormat() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDataPath() {
		return dataPath;
	}

	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}

}
