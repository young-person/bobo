package com.app.service;

import com.app.crawler.riches.RicheTarget;

import java.util.List;


public interface ReceiveRiches {

	/**
	 * 使用邮件发送数据
	 * @param datas
	 */
	void receiveRichesData(List<RicheTarget> datas);

	/**
	 * 发送监控数据
	 */
	void sendScheduleData();
}
