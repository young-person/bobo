package com.app.crawler.riches.pojo;

import java.util.Objects;

public class RShareInfo {
	/**
	 * 日期
	 */
	private String date;

	/**
	 * 换手率
	 */
	private float hand;

	/**
	 * 涨跌幅度比例
	 */
	private float risePrice;

	/**
	 * 开盘价
	 */
	private float openPrice;
	/**
	 * 收盘价
	 */
	private float closePrice;

	/**
	 * 昨日收盘价
	 */

	private float prevClose;

	/**
	 * 今日最高价
	 */
	private float maxPrice;
	/**
	 * 今日最低价
	 */
	private float minPrice;
	/**
	 * 成交总量
	 */
	private float total;
	/**
	 * 总金额
	 */
	private double money;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public float getHand() {
		return hand;
	}
	public void setHand(float hand) {
		this.hand = hand;
	}
	public float getRisePrice() {
		return risePrice;
	}
	public void setRisePrice(float risePrice) {
		this.risePrice = risePrice;
	}
	public float getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(float openPrice) {
		this.openPrice = openPrice;
	}
	public float getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(float closePrice) {
		this.closePrice = closePrice;
	}
	public float getPrevClose() {
		return prevClose;
	}
	public void setPrevClose(float prevClose) {
		this.prevClose = prevClose;
	}
	public float getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(float maxPrice) {
		this.maxPrice = maxPrice;
	}
	public float getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(float minPrice) {
		this.minPrice = minPrice;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, hand, risePrice, openPrice, closePrice, prevClose, maxPrice, minPrice, total, money);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RShareInfo other = (RShareInfo) obj;
		if (Float.floatToIntBits(closePrice) != Float.floatToIntBits(other.closePrice))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (Float.floatToIntBits(hand) != Float.floatToIntBits(other.hand))
			return false;
		if (Float.floatToIntBits(maxPrice) != Float.floatToIntBits(other.maxPrice))
			return false;
		if (Float.floatToIntBits(minPrice) != Float.floatToIntBits(other.minPrice))
			return false;
		if (Double.doubleToLongBits(money) != Double.doubleToLongBits(other.money))
			return false;
		if (Float.floatToIntBits(openPrice) != Float.floatToIntBits(other.openPrice))
			return false;
		if (Float.floatToIntBits(prevClose) != Float.floatToIntBits(other.prevClose))
			return false;
		if (Float.floatToIntBits(risePrice) != Float.floatToIntBits(other.risePrice))
			return false;
		if (total != other.total)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "RShareInfo [date=" + date + ", hand=" + hand + ", risePrice=" + risePrice + ", openPrice=" + openPrice
				+ ", closePrice=" + closePrice + ", prevClose=" + prevClose + ", maxPrice=" + maxPrice + ", minPrice="
				+ minPrice + ", total=" + total + ", money=" + money + "]";
	}
	public RShareInfo(String date, float hand, float risePrice, float openPrice, float closePrice, float prevClose,
			float maxPrice, float minPrice, float total, double money) {
		super();
		this.date = date;
		this.hand = hand;
		this.risePrice = risePrice;
		this.openPrice = openPrice;
		this.closePrice = closePrice;
		this.prevClose = prevClose;
		this.maxPrice = maxPrice;
		this.minPrice = minPrice;
		this.total = total;
		this.money = money;
	}
	public RShareInfo() {
		super();
	}
	
}
