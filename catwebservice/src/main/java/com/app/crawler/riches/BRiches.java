package com.app.crawler.riches;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.app.crawler.CrawlerDown;
import com.app.crawler.riches.pojo.HistoryResult;
import com.app.crawler.riches.pojo.RicheBean;
import com.app.crawler.riches.pojo.RicheResult;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BRiches implements CrawlerDown {
	
	/**
	 * Excel 数据模板路径地址
	 */
	private String dataPath;
	
	private final ObjectMapper mapper = new ObjectMapper();
	
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
	private String stockUrl = "https://m.stock.pingan.com/";
	
	private String stockAppUrl = "https://m.stock.pingan.com/h5quote/quote/getReportData";
	
	private final String num = "600";
	
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
	}};
	
	///restapi/servicecenter/option/homeIcon/customIconById
	/**
	 * 月线
	 */
	//https://m.stock.pingan.com/h5quote/quote/getDayDataBigInt?random=0.060092707195325445&thirdAccount=&rdm=&timestamp=&tokenId=&signature=&key=&chnl=&requestId=&stockCode=300797&codeType=4621&period=month&day=50
	/**
	 * 周线
	 */
	//https://m.stock.pingan.com/h5quote/quote/getDayDataBigInt?random=0.8903233738548277&thirdAccount=&rdm=&timestamp=&tokenId=&signature=&key=&chnl=&requestId=&stockCode=000917&codeType=4609&period=week&day=50
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


	public void init() {
		
		
	}
	RestTemplate restTemplate = new RestTemplate();
	
	/**
	 * 股票类型
	 */
	final Map<String, String> typeName = new HashMap<String, String>(){
		private static final long serialVersionUID = 6893069965305064089L;
	{
		put("4621", "创业");
		put("4614", "深证");
		put("4353", "沪A");
		put("4609", "深A");
	}};
	/**
	 * 换手率
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
		LOGGER.info("换手率请求URL:[{}]",url);
		RicheResult handResult = restTemplate.getForObject(new URI(url), RicheResult.class);

		return handResult;
	}
	
	/**
	 * 获取所有股票今日数据
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
		LOGGER.info("获取所有股票今日数据请求URL:[{}]",url);
		RicheResult handResult = restTemplate.getForObject(new URI(url), RicheResult.class);

		return handResult;
	}
	
	@Override
	public void start() {
		Map<String, String> handMap = new HashMap<>();
		
		try {
			RicheResult handResult = getTurnoverRate();

			for(RicheBean bean : handResult.getResults()) {
				handMap.put(bean.getCode(), bean.getHand());
			}
			RicheResult result = this.getTodayDatas();

			List<String> names = new ArrayList<String>();
			/**
			 * 获取所有股票
			 */
			for(RicheBean bean : result.getResults()) {
				/**
				 * 4353 沪A
				 * 4614 深证
				 * 4609 深A
				 */
				if (!Arrays.asList("4353","4614","4609").contains(bean.getCodeType())) {
					continue;
				}
				bean.setHand(handMap.get(bean.getCode()));
				boolean sure = this.mindTrend(this.num, bean.getCode(), bean.getCodeType(),bean.getStockName());
				if (sure) {
					names.add(bean.getStockName());
				}
				
			}
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
	public boolean mindTrend(String days,String stockCode,String codeType,String name) {
		boolean sure = false;
		String url = String.format(dayUrl, stockCode,codeType,days);
		LOGGER.info("股票：【{}】,获取历史数据URL：【{}】",name,url);
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			HistoryResult historyResult = restTemplate.getForObject(new URI(url), HistoryResult.class);
			RicheTarget target = richeCompute.compute(historyResult,name);
			if (target.getL() > 0.5) {
				sure = true;
			}
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return sure;
	}
	private String getUrl(Map<String, String> parmas,String url) {
		StringBuilder builder =  new StringBuilder(url);
		boolean flag = true;
		if (Objects.nonNull(parmas)) {
			for(String key : parmas.keySet()) {
				if (flag) {
					builder.append("?");
					flag = false;
				}else {
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
	

}
