package com.app.crawler.riches.pojo;

import java.io.Serializable;

/** 
 * @Description: TODO
 * @date 2019年11月2日 上午8:15:49 
 * @ClassName: HistoryBean 
 */
public class HistoryBean implements Serializable{

	/** 
	 * @Fields serialVersionUID : TODO
	 * @data 2019年11月2日 上午8:28:19 
	 */ 
	private static final long serialVersionUID = -4601440188712801008L;
	/**
	 * 日期
	 */
	private String date;

	/**
	 * 开盘价
	 */
	private String openPrice;
	/**
	 * 收盘价
	 */
    private String closePrice;
	/**
	 * 今日最高价
	 */
    private String maxPrice;
	/**
	 * 今日最低价
	 */
    private String minPrice;
	/**
	 * 成交总量
	 */
    private String total;
	/**
	 * 总金额
	 */
    private String money;
	/**
	 * 
	 */
    private String ma5;
	/**
	 * 
	 */
    private String ma10;
	/**
	 * 
	 */
    private String ma30;

	/**
	 * 换手率
	 */
	private String hand;

	/**
	 * 涨跌幅度比例
	 */
	private String risePrice;
	/**
	 * 涨跌额度
	 */
	private String riseMoney;

	public String getRiseMoney() {
		return riseMoney;
	}

	public void setRiseMoney(String riseMoney) {
		this.riseMoney = riseMoney;
	}

	public String getRisePrice() {
		return risePrice;
	}

	public void setRisePrice(String risePrice) {
		this.risePrice = risePrice;
	}

	public String getHand() {
		return hand;
	}

	public void setHand(String hand) {
		this.hand = hand;
	}

	public String getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(String openPrice) {
		this.openPrice = openPrice;
	}
	public String getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(String closePrice) {
		this.closePrice = closePrice;
	}
	public String getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}
	public String getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMa5() {
		return ma5;
	}
	public void setMa5(String ma5) {
		this.ma5 = ma5;
	}
	public String getMa10() {
		return ma10;
	}
	public void setMa10(String ma10) {
		this.ma10 = ma10;
	}
	public String getMa30() {
		return ma30;
	}
	public void setMa30(String ma30) {
		this.ma30 = ma30;
	}
	@Override
	public String toString() {
		return "HistoryBean [openPrice=" + openPrice + ", closePrice=" + closePrice + ", maxPrice=" + maxPrice
				+ ", minPrice=" + minPrice + ", total=" + total + ", money=" + money + ", date=" + date + ", ma5=" + ma5
				+ ", ma10=" + ma10 + ", ma30=" + ma30 + "]";
	}
    
}
