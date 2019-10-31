package com.app.crawler.riches;

import com.app.crawler.CrawlerDown;
import com.app.utils.HttpUtil;

public class BRiches implements CrawlerDown{

	public static void main(String[] args) {
		BRiches bRiches = new BRiches();
		bRiches.start();
	}
	private String url = "http://quote.eastmoney.com/stocklist.html";
	@Override
	public void start() {
		// TODO Auto-generated method stub
		String content = HttpUtil.doGetRequest(url);
		System.out.println(content);
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
