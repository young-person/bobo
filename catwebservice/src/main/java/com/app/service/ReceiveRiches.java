package com.app.service;

import com.app.crawler.riches.RicheTarget;

import java.util.concurrent.CopyOnWriteArraySet;


public interface ReceiveRiches {

	/**
	 * 使用邮件发送数据
	 * @param datas
	 */
	void receiveRichesData(CopyOnWriteArraySet<RicheTarget> datas);

	/**
	 * 发送监控数据
	 */
	void sendScheduleData();
}
